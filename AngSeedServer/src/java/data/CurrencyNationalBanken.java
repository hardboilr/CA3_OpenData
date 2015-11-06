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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CurrencyNationalBanken extends DefaultHandler implements Runnable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private static List<DailyRate> dailyRates;
    List<DailyRate> tmpDailyRates;
    private int count = 0;
    private java.sql.Date date;

    public CurrencyNationalBanken() {
        dailyRates = new ArrayList();
        tmpDailyRates = new ArrayList();
    }

    @Override
    public void startDocument() throws SAXException {
        tmpDailyRates.clear();
    }

    @Override
    public void endDocument() throws SAXException {
        dailyRates = tmpDailyRates;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (DailyRate dailyRate : dailyRates) {
                Currency cur = em.find(Currency.class, dailyRate.getCurrency().getCurrencyCode());
                if (cur != null) { //found existing currency
                    dailyRate.setCurrency(cur);
                }
                em.persist(dailyRate);
                em.persist(cur);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (count == 1) { //get date
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.getValue(i));
                date = java.sql.Date.valueOf(attributes.getValue(i));
                System.out.println(date);
            }
        } else if (count > 1) { //get other data
            boolean isOk = true;
            DailyRate rate = new DailyRate();
            Currency cur = new Currency();
            for (int i = 0; i < attributes.getLength(); i++) {
                switch (attributes.getLocalName(i)) {
                    case "code":
                        cur.setCurrencyCode(attributes.getValue(i));
                        break;
                    case "desc":
                        cur.setName(attributes.getValue(i));
                        break;
                    case "rate":
                        try {
                            rate.setValue(Float.parseFloat(attributes.getValue(i)));
                        } catch (NumberFormatException e) {
                            rate.setValue(0f);
                        }
                        break;
                    default:
                        isOk = false;
                        break;
                }
            }
            if (isOk) {
                rate.setDateField(date);
                rate.setCurrency(cur);
                tmpDailyRates.add(rate);
            }
        }
        count++;
    }

    public static List<DailyRate> getDailyRates() {
        return dailyRates;
    }

    @Override
    public void run() {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(this);
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
