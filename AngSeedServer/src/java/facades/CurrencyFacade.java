package facades;

import data.CurrencyNationalBanken;
import deploy.DeploymentConfiguration;
import entity.Currency;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CurrencyFacade {
    
    private EntityManagerFactory emf;
    private CurrencyNationalBanken cnb;
    
    public CurrencyFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        cnb = new CurrencyNationalBanken();
    }
    
    public List<Currency> getCurrencies() {
      return cnb.getDailyRates();
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
   
    
}
