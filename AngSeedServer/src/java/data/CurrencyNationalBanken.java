package data;

import deploy.DeploymentConfiguration;
import entity.Currency;
import entity.DailyRate;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CurrencyNationalBanken extends DefaultHandler {

    private EntityManagerFactory emf;
    private List<DailyRate> dailyRates;
    private boolean isCached;
    private java.sql.Date dateField;

    public CurrencyNationalBanken() {
        cache();
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Document (Sax-event)");

    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End Document (Sax-event)");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        EntityManager em = getEntityManager();
        System.out.print("Element: " + localName + ": ");

        Currency cur = new Currency();

        DailyRate dailyRate = new DailyRate();

        for (int i = 0; i < attributes.getLength(); i++) {

            switch (attributes.getLocalName(i)) {

                case "id":
                    String date1 = attributes.getValue(i);
                    dateField = java.sql.Date.valueOf(date1);
                    break;
                case "code":
                    cur = em.find(Currency.class, attributes.getValue(i));
                    dailyRate.setCurrency(cur);
                    break;
                case "rate":
                    dailyRate.setValue(Float.parseFloat(attributes.getValue(i)));
                    dailyRate.setDateField(dateField);
                    break;

                default:
                    break;
            }
            
            em.getTransaction().begin();
            em.persist(dailyRate);
            em.getTransaction().commit();
            em.close();

            dailyRates.add(dailyRate);
            isCached = true;

            System.out.print("[Atribute: NAME: " + attributes.getLocalName(i) + " VALUE: " + attributes.getValue(i) + "] ");
        }
        System.out.println("");
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
                return null;
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] argv) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new CurrencyNationalBanken());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void cache() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isCached = false;
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 24, TimeUnit.HOURS);
    }

    public boolean isCached() {
        return isCached;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
