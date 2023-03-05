package com.arcadag.summapropisyu.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.allegro.finance.tradukisto.MoneyConverters;
import java.util.Map;

@Component
@Getter
public class CurrencyCollection {
    private Map<String, MoneyConverters> currencyMap;
    private Map<Integer, String> kindOfVat;

    public CurrencyCollection() {
        fillCurrencyMap();
        fillKindOfVat();
    }

    private void fillCurrencyMap() {
        this.currencyMap = Map.of(
                "RUB", MoneyConverters.RUSSIAN_BANKING_MONEY_VALUE,
                "USD", MoneyConverters.ENGLISH_BANKING_MONEY_VALUE,
                "EUR", MoneyConverters.ENGLISH_BANKING_MONEY_VALUE
        );
    }
    private void fillKindOfVat() {
        this.kindOfVat = Map.of(
                0, "НДС",
                7, "НДС",
                10, "НДС",
                12, "НДС",
                17, "НДС",
                15, "НДС",
                18, "НДС",
                20, "НДС",
                13, "НДФЛ"
        );
    }
}
