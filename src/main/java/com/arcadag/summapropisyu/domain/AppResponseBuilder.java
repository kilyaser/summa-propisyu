package com.arcadag.summapropisyu.domain;

import com.arcadag.summapropisyu.dtos.AppResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppResponseBuilder {
    private AppResponseDto appResponse;
    private final CurrencyCollection currencyCollection;

    public AppResponseBuilder create() {
        appResponse = new AppResponseDto();
        appResponse.setResponses(new ArrayList<>());

        return this;
    }

    public AppResponseBuilder addResponses(DataModel dataModel) {
        if (currencyCollection.getCurrencyMap().containsKey(dataModel.getCurrency())) {
            appResponse.getResponses().add(getResponseV1(dataModel));
            appResponse.getResponses().add(getResponseV2(appResponse.getResponses().get(0)));

            for (String string : appResponse.getResponses()) {
                log.info("stringBuilder: {}", string);
            }
//            log.info("coin: {}", dataModel.getCoin());
//            log.info("vat: {}", dataModel.getVat());
//            log.info("totalSum: {}", dataModel.getTotal());

            //        dataModel.setVat(BigDecimal.valueOf(Float.valueOf(dto.getSum())).setScale(2, RoundingMode.FLOOR)
//                        .divide(BigDecimal.valueOf(100))
//                        .multiply(BigDecimal.valueOf(dto.getInputVAT())));

        }

        return this;
    }

    public AppResponseDto getAppResponses() {
        return appResponse;
    }

    private String getResponseV1(DataModel dataModel){
        var converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        var stringBuilder = new StringBuilder();

        stringBuilder
                .append(cutTailOfString(converters.asWords(dataModel.getSumWithoutCoin()), 8))
                .append(getRubModification(dataModel))
                .append(getCoinModification(dataModel));
        return stringBuilder.toString();
    }

    private String getResponseV2(String data) {
        var stringBuilder = new StringBuilder(data.substring(0, 1).toUpperCase());
        return stringBuilder.append(data.substring(1)).toString();
    }


    private StringBuilder cutTailOfString(String str, int endSize) {
        StringBuilder result = new StringBuilder(str.substring(0, str.length() - endSize));
        return result;
    }

    private String getRubModification(DataModel dataModel){
        var str = dataModel.getSumWithoutCoin().toString();
        int i = Integer.parseInt(str.substring(str.length() - 1));

        if (i == 1) {
            return "ль ";
        } else if (i > 0 && i < 5) {
            return "ля ";
        } else {
            return "лей ";
        }
    }

    private String getCoinModification(DataModel dataModel) {
        var converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        String string = dataModel.getCoin().intValue() >= 10 ? dataModel.getCoin().toString() : "0" + dataModel.getCoin();

        String[] mas = string.split("");

        var stringBuilder = new StringBuilder();

        if (mas[1].equals("1") && !mas[0].equals("1")) {
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append("одна копейка");
        } else if (mas[1].equals("2") && !mas[0].equals("1")){
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append("две копейки");
        } else if ((mas[1].equals("3") || mas[1].equals("4")) && !mas[0].equals("1")) {
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[1]))), 11));
            stringBuilder.append("копейки");
        } else {
            stringBuilder.append(cutTailOfString(converters.asWords(dataModel.getCoin()), 11));
            stringBuilder.append("копеек");
        }

        return stringBuilder.toString();
    }




}
