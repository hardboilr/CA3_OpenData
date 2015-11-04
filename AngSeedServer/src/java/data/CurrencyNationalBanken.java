package data;

import deploy.DeploymentConfiguration;
import entity.Currency;
import entity.DailyRate;
import facades.CurrencyFacade;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CurrencyNationalBanken extends DefaultHandler {

    private EntityManagerFactory emf;
    private List<DailyRate> dailyRates = new ArrayList();;
    private boolean isCached;
    private java.sql.Date dateField;
    private int count = 0;
    private java.sql.Date date;

    public CurrencyNationalBanken() {
//        cache();
        
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        for(DailyRate rate : dailyRates){
            em.persist(rate);
        }
        em.getTransaction().commit();
        em.close();
//        isCached = true;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (count == 1){
            for (int i = 0; i < attributes.getLength(); i++) {
                date = java.sql.Date.valueOf(attributes.getValue(i));
            }
        } else if (count > 1){
            DailyRate rate = new DailyRate();
            EntityManager em = getEntityManager();
            for (int i = 0; i < attributes.getLength(); i++) {
                switch (attributes.getLocalName(i)){
                    case "code" : 
                        Currency cur = em.find(Currency.class, attributes.getValue(i)); 
                        rate.setCurrency(cur);
                        rate.setDateField(date);
                        break;
                    case "rate" :
                        try {
                        rate.setValue(Float.parseFloat(attributes.getValue(i)));
                        } catch(NumberFormatException e){
                            rate.setValue(0f);
                        }
                        break;
                    default : break;    
                }
            }
            CurrencyFacade.addDailyRate(rate);
        }
        count++;
     }   
     
      

    public List<DailyRate> getDailyRates() {
        if (isCached) {
            return dailyRates;
        } else {
            try {
                XMLReader xr = XMLReaderFactory.createXMLReader();
                xr.setContentHandler(new CurrencyNationalBanken());
                URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
                xr.parse(new InputSource(url.openStream()));
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return dailyRates;
    }

    private void cache() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isCached = false;
                getDailyRates();
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
        
    }

    public boolean isCached() {
        return isCached;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
