package facades;

import data.CurrencyNationalBanken;
import entity.DailyRate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CurrencyFacade {

    private EntityManagerFactory emf;

    public CurrencyFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Returns a list of currency rates for the current day If dailyrates have
     * not yet been cached, get the rates from nationalBanken, otherwise used
     * the cached data
     *
     * @return
     */
    public List<DailyRate> getDailyRates() {
        return CurrencyNationalBanken.getDailyRates();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
