package zw.co.econet.ecorecutilitytests.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import zw.co.econet.ecorecutilitytests.entities.*;
import zw.co.econet.ecorecutilitytests.services.ConfigurationService;
import zw.co.econet.ecorecutilitytests.services.InventoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceUtilsTest {


    @Mock
    private ConfigurationService configurationService;

    @Mock
    private InventoryService inventoryService;

    private PaymentServiceUtils paymentServiceUtils;
    private Payment payment;
    private Customer customer;
    private StockItem bonusStockItem;
    private Price price;
    private CurrencyRateTable currencyRateTable;
    private CurrencyRate currencyRate;
    private Currency localCurrency;

    @Before
    public void setup(){
        paymentServiceUtils = new PaymentServiceUtils();
        payment = new Payment();
        payment.setAmountDue(2);
        payment.setCurrency("USD");

        customer = new Customer();
        customer.setPriceLevel("1");

        bonusStockItem = new StockItem();
        bonusStockItem.setStockItemCode("VLTPBONUS01");

        price = new Price();
        price.setPrice(15);

        localCurrency = new Currency();
        localCurrency.setName("ZWL");

        currencyRate = new CurrencyRate();
        currencyRate.setCurrency(localCurrency);
        currencyRate.setRate(25);

        List<CurrencyRate> currencyRateList = new ArrayList<>();
        currencyRateList.add(currencyRate);

        currencyRateTable = new CurrencyRateTable();
        currencyRateTable.setCurrencyRate(currencyRateList);

    }
    @Test
    public void calculateVoucherlessBonusShouldPassIfCurrencyIsElligible() throws Exception {
        final double expectedAmount = 80.00;
       // when(configurationService.getStringProperty("VOUCHERLESS_ELIGIBLE_CURRENCY")).thenReturn("USD");
        // when(configurationService.getStringProperty("VOUCHERLESS_BONUS_STOCKCODE")).thenReturn("VLTPBONUS01");
        when(inventoryService.findStockItemByItemCode(anyString())).thenReturn(bonusStockItem);
        when(inventoryService.findPriceByStockItemCodeAndPriceLevel(anyString(), anyString())).thenReturn(price);
        when(inventoryService.findCurrencyRateTable(any(Date.class))).thenReturn(currencyRateTable);
        when(inventoryService.findCurrencyRateByCurrencyRateTable(anyString(), any(CurrencyRateTable.class))).thenReturn(currencyRate);

        final double response = paymentServiceUtils.calculateVoucherlessBonus(payment);

        assertEquals(expectedAmount, response);
    }

}
