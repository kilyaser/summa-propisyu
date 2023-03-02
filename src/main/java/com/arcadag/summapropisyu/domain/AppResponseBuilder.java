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
            appResponse.getResponses().add(getResponseV3(dataModel));
            appResponse.getResponses().add(getResponseV4(dataModel));

            for (String string : appResponse.getResponses()) {
                log.info("stringBuilder: {}", string);
            }

        }

        return this;
    }

    public AppResponseDto getAppResponses() {
        return appResponse;
    }

    private String getResponseV1(DataModel dataModel){
        var stringBuilder = new StringBuilder();

        return stringBuilder
                .append(getRublePart(dataModel))
                .append(getCoinPart(dataModel))
                .toString();
    }

    private String getResponseV2(String data) {
        var stringBuilder = new StringBuilder(data.substring(0, 1).toUpperCase());
        return stringBuilder.append(data.substring(1)).toString();
    }

    private String getResponseV3(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        return stringBuilder
                .append(getRublePart(dataModel))
                .append(dataModel.getCoin())
                .append(" ")
                .append(getCoinTail(dataModel))
                .toString();
    }

    private String getResponseV4(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
            stringBuilder.append(getResponseV1(dataModel));

            if (dataModel.getVat() == 0)  return stringBuilder.append(", НДС не облагается").toString();

            return stringBuilder
                    .append(", в т.ч. ")
                    .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                    .append(" (").append(dataModel.getVat()).append("%) ")
                    .append(dataModel.getSumOfVat())
                    .append( " руб.").toString();
    }

    private String getRublePart(DataModel dataModel) {
        var converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        var stringBuilder = new StringBuilder();
        return stringBuilder
                .append(cutTailOfString(converters.asWords(dataModel.getSumWithoutCoin()), 8))
                .append(getRubModification(dataModel))
                .toString();
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


    private String getCoinPart(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        stringBuilder
                .append(getCoinModification(dataModel))
                .append(getCoinTail(dataModel));

        return stringBuilder.toString();
    }


    private String getCoinModification(DataModel dataModel) {
        var converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        String string = dataModel.getCoin().intValue() >= 10 ? dataModel.getCoin().toString() : "0" + dataModel.getCoin();

        String[] mas = string.split("");

        var stringBuilder = new StringBuilder();

        if (mas[1].equals("1") && !mas[0].equals("1")) {
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append("одна ");
        } else if (mas[1].equals("2") && !mas[0].equals("1")){
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append("две ");
        } else if ((mas[1].equals("3") || mas[1].equals("4")) && !mas[0].equals("1")) {
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), 11));
            stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[1]))), 11));
        } else {
            stringBuilder.append(cutTailOfString(converters.asWords(dataModel.getCoin()), 11));
        }

        return stringBuilder.toString();
    }

    private String getCoinTail(DataModel dataModel) {
        String string = dataModel.getCoin().intValue() >= 10 ? dataModel.getCoin().toString() : "0" + dataModel.getCoin();
        String[] mas = string.split("");
        var stringBuilder = new StringBuilder();

        if (mas[1].equals("1") && !mas[0].equals("1")) {
            stringBuilder.append("копейка");
        } else if (mas[1].equals("2") && !mas[0].equals("1")){
            stringBuilder.append("копейки");
        } else if ((mas[1].equals("3") || mas[1].equals("4")) && !mas[0].equals("1")) {
            stringBuilder.append("копейки");
        } else {
            stringBuilder.append("копеек");
        }

        return stringBuilder.toString();

    }




}
