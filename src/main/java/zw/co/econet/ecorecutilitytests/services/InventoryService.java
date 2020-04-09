package zw.co.econet.ecorecutilitytests.services;

import zw.co.econet.ecorecutilitytests.entities.*;

import java.util.Date;

public interface InventoryService {
    public StockItem findStockItemByItemCode(String stockCode);
    public Price findPriceByStockItemCodeAndPriceLevel(String stockItemCode, String priceLevelCode);
    public CurrencyRateTable findCurrencyRateTable(Date date);
    public CurrencyRate findCurrencyRateByCurrencyRateTable(String currency, CurrencyRateTable currencyRateTable);
}
