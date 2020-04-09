package zw.co.econet.ecorecutilitytests.utils;

import zw.co.econet.ecorecutilitytests.entities.*;
import zw.co.econet.ecorecutilitytests.services.ConfigurationService;
import zw.co.econet.ecorecutilitytests.services.InventoryService;

import java.util.Date;

public class PaymentServiceUtils {
    private ConfigurationService configurationService;
    private InventoryService inventoryService;

    public double calculateVoucherlessBonus(Payment payment) throws Exception{

        String eligibleCurrency = "USD"; // configurationService.getStringProperty("VOUCHERLESS_ELIGIBLE_CURRENCY");
        if(eligibleCurrency ==null){
            throw new Exception("Please contact your Adminstartor, no property VOUCHERLESS_ELIGIBLE_CURRENCY");
        }
        if (payment.getCurrency().equalsIgnoreCase(eligibleCurrency)){
            String bonusStockCode = "VLTPBONUS01"; //configurationService.getStringProperty("VOUCHERLESS_BONUS_STOCKCODE");
            if(bonusStockCode == null){
                throw new Exception("Please contact your Adminstartor, no property VOUCHERLESS_BONUS_STOCKCODE");
            }

            StockItem bonusStockItem = inventoryService.findStockItemByItemCode(bonusStockCode);
            String priceLevelCode = payment.getCustomer().getPriceLevel();
            Price price = inventoryService.findPriceByStockItemCodeAndPriceLevel(bonusStockItem.getStockItemCode(), priceLevelCode);

            if(price == null){
                payment.setResponseCode("No Price defined for st");
                throw new IllegalStateException("No price defined");
            }
            double unitPrice = price.getPrice();

            CurrencyRateTable currencyRateTable = inventoryService.findCurrencyRateTable(new Date());
            if(currencyRateTable == null){
                throw new IllegalStateException("No currency table exists");
            }
            CurrencyRate currencyRate = inventoryService.findCurrencyRateByCurrencyRateTable("ZWL", currencyRateTable);
            if(currencyRate == null){
                throw new  IllegalStateException("No currency Rate");
            }
            double ratedAmount = payment.getAmountDue() * currencyRate.getRate();
            double newAmount = ratedAmount + (payment.getAmountDue() * unitPrice);
            return newAmount;
        }
        return payment.getAmountDue();
    }



}
