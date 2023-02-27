package com.arcadag.summapropisyu.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.allegro.finance.tradukisto.MoneyConverters;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CurrencyCollection {
    private Map<String, MoneyConverters> currencyMap;

    public CurrencyCollection() {
        fillCurrencyMap();
    }

    private void fillCurrencyMap() {
        this.currencyMap = new HashMap<>();
        currencyMap.put("RUB", MoneyConverters.RUSSIAN_BANKING_MONEY_VALUE);
        currencyMap.put("USD", MoneyConverters.ENGLISH_BANKING_MONEY_VALUE);
        currencyMap.put("EUR", MoneyConverters.ENGLISH_BANKING_MONEY_VALUE);
    }
}
