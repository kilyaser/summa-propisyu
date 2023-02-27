package com.arcadag.summapropisyu.domain;

import com.arcadag.summapropisyu.dtos.AppResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.allegro.finance.tradukisto.MoneyConverters;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppResponseBuilder {
    private AppResponseDto appResponse;
    private final CurrencyCollection currencyCollection;

    public AppResponseBuilder create() {
        appResponse = new AppResponseDto();
        return this;
    }

    public AppResponseBuilder addResponses(DataModel dataModel) {
        if (currencyCollection.getCurrencyMap().containsKey(dataModel.getCurrency())) {
            MoneyConverters converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
//            log.info("from MoneyService sum: {}", converters.asWords(dataModel.getSumWithoutCoin()));
//            log.info("from MoneyService coin: {}", dataModel.getCoin());
//            log.info("from MoneyService vat: {}", dataModel.getVat());
//            log.info("from MoneyService totalSum: {}", dataModel.getTotal());
        }
        return this;
    }

    public AppResponseDto getAppResponses() {
        return appResponse;
    }

}
