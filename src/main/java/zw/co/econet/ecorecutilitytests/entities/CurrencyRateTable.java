package zw.co.econet.ecorecutilitytests.entities;

import java.util.Date;
import java.util.List;

public class CurrencyRateTable {
    private Date date;
    private List<CurrencyRate> currencyRate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CurrencyRate> getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(List<CurrencyRate> currencyRate) {
        this.currencyRate = currencyRate;
    }
}
