package zw.co.econet.ecorecutilitytests.entities;

public class Currency {
    private String name;
    private boolean isBaseCurrency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaseCurrency() {
        return isBaseCurrency;
    }

    public void setBaseCurrency(boolean baseCurrency) {
        isBaseCurrency = baseCurrency;
    }
}
