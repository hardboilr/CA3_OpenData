package facades;

import data.CurrencyNationalBanken;
import entity.DailyRate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CurrencyFacade {
    
    private EntityManagerFactory emf;
    private CurrencyNationalBanken cnb;
    
    private static List<DailyRate> rates; 
    
    public CurrencyFacade(EntityManagerFactory emf) {
        this.emf = emf;
        cnb = new CurrencyNationalBanken();
        rates = new ArrayList();
    }
    
    public List<DailyRate> getDailyRates() {
     System.out.println("ctrl get dailyrates");  
     cnb.getDailyRates();
     return rates;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void addDailyRate(DailyRate rate){
        rates.add(rate);
    }
    
   
    
}
