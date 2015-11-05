package facades;

import data.CurrencyNationalBanken;
import entity.DailyRate;
import java.util.List;

public class CurrencyFacade {


    public CurrencyFacade() {
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
}
