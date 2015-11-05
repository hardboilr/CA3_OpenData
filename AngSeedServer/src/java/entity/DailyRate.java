package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DailyRate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private java.sql.Date dateField;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Currency currency;

    private float value;

    public DailyRate() {
    }

    public DailyRate(Date dateField, Currency currency, float value) {
        this.dateField = dateField;
        this.currency = currency;
        this.value = value;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
//        currency.addDailyRate(this);
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
