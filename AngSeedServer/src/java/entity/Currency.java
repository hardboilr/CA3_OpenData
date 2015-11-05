package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Currency implements Serializable {

    @Id
    private String currencyCode;

    private String name;

    @ElementCollection
    @OneToMany(mappedBy = "currency", cascade = CascadeType.PERSIST)
    private List<DailyRate> dailyRates;

    public Currency() {
    }

    public Currency(String code, String name) {
        this.currencyCode = code;
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String code) {
        this.currencyCode = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DailyRate> getDailyRates() {
        return dailyRates;
    }

    public void setDailyRates(List<DailyRate> dailyRates) {
        this.dailyRates = dailyRates;
    }

    public void addDailyRate(DailyRate dailyRate) {
        dailyRate.setCurrency(this);
        dailyRates.add(dailyRate);
    }
}
