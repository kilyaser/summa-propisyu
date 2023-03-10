package com.arcadag.summapropisyu.domain;

import com.arcadag.summapropisyu.converter.FormDataDtoConverter;
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
    private static final Integer TAIL_CUTTER_SIZE = 11;
    private final CurrencyCollection currencyCollection;
    private final FormDataDtoConverter formDataDtoConverter;
    private final FormDataDtoBuilder formDataDtoBuilder;

    public AppResponseBuilder create() {
        appResponse = new AppResponseDto();
        appResponse.setResponses(new ArrayList<>());

        return this;
    }

    public AppResponseBuilder addResponses(DataModel dataModel) {
        if (currencyCollection.getCurrencyMap().containsKey(dataModel.getCurrency())) {
            appResponse.getResponses().add(getResponseV1(dataModel));
            appResponse.getResponses().add(getResponseV2(dataModel));
            appResponse.getResponses().add(getResponseV3(dataModel));
            appResponse.getResponses().add(getResponseV4(dataModel));
            appResponse.getResponses().add(getResponseV5(dataModel));
            appResponse.getResponses().add(getResponseV6(dataModel));
            appResponse.getResponses().add(getResponseV7(dataModel));
            appResponse.getResponses().add(getResponseV8(dataModel));
            appResponse.getResponses().add(getResponseV9(dataModel));
            appResponse.getResponses().add(getResponseV10(dataModel));
            appResponse.getResponses().add(getResponseV11(dataModel));
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

    private String getResponseV2(DataModel dataModel) {
        var string = getResponseV1(dataModel);
        var stringBuilder = new StringBuilder(string.substring(0, 1).toUpperCase());
        return stringBuilder.append(string.substring(1)).toString();
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
                    .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                    .append( " руб.").toString();
    }

    private String getResponseV5(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
         return stringBuilder
                 .append(getResponseV4(dataModel))
                 .append(" (")
                 .append(getResponseV1(dataModel))
                 .append(")").toString();
    }

    private String getResponseV6(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var formDataDtoOfSumOfVat = formDataDtoBuilder.getFromData(dataModel);

        return stringBuilder
                .append(dataModel.getTotal().toString().replace(".", dataModel.getPoint()))
                .append(" руб.")
                .append(" (")
                .append(getResponseV1(dataModel))
                .append("), в т.ч. ")
                .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                .append(" (")
                .append(dataModel.getVat())
                .append("%) ")
                .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                .append(" руб. (")
                .append(getResponseV1(formDataDtoConverter.formDataDtoToDataModel(formDataDtoOfSumOfVat)))
                .append(")").toString();
    }

    private String getResponseV7(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var formDataDtoOfSumOfVat = formDataDtoBuilder.getFromData(dataModel);

        return stringBuilder
                .append(dataModel.getTotal().toString().replace(".", dataModel.getPoint()))
                .append(" руб.")
                .append(" (")
                .append(getResponseV1(dataModel))
                .append("), в т.ч. ")
                .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                .append(" (")
                .append(dataModel.getVat())
                .append("%) ")
                .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                .append(" руб. (")
                .append(getResponseV2(formDataDtoConverter.formDataDtoToDataModel(formDataDtoOfSumOfVat)))
                .append(")").toString();
    }

    private String getResponseV8(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var formDataDtoOfSumOfVat = formDataDtoBuilder.getFromData(dataModel);

        stringBuilder.append(dataModel.getTotal().toString().replace(".", dataModel.getPoint()))
                .append(" руб. (");

        var string = getResponseV3(dataModel);

        stringBuilder.append(string.substring(0, 1).toUpperCase())
                .append(string.substring(1))
                .append(")")
                .append(", в т.ч. ")
                .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                .append(" ")
                .append(dataModel.getVat())
                .append("%")
                .append(" ")
                .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                .append(" руб.")
                .append(" (");
        var vatString = getResponseV3(formDataDtoConverter.formDataDtoToDataModel(formDataDtoOfSumOfVat));

        return stringBuilder.append(vatString.substring(0, 1).toUpperCase())
                .append(vatString.substring(1))
                .append(")").toString();

    }
    private String getResponseV9(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var dataModelOfSumOfVat = formDataDtoConverter.formDataDtoToDataModel(formDataDtoBuilder.getFromData(dataModel));
        var converter = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());

        return stringBuilder
                .append(dataModel.getSumWithoutCoin())
                .append(" (")
                .append(cutTailOfString(converter.asWords(dataModel.getSumWithoutCoin()), TAIL_CUTTER_SIZE + 1))
                .append(") ")
                .append(getFullRubModification(dataModel))
                .append(dataModel.getCoin())
                .append(" ")
                .append(getCoinTail(dataModel))
                .append(", ")
                .append("в том числе ")
                .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                .append("- ")
                .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                .append(" (")
                .append(cutTailOfString(converter.asWords(dataModelOfSumOfVat.getSumWithoutCoin()), TAIL_CUTTER_SIZE + 1))
                .append(") ")
                .append(getFullRubModification(dataModelOfSumOfVat))
                .append(dataModelOfSumOfVat.getCoin())
                .append(" ")
                .append(getCoinTail(dataModelOfSumOfVat))
                .toString();
    }
    private String getResponseV10(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var converter = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());

        stringBuilder
                .append(dataModel.getSumWithoutCoin())
                .append(" (");
        var string = cutTailOfString(converter.asWords(dataModel.getSumWithoutCoin()), TAIL_CUTTER_SIZE + 1);
        return stringBuilder.append(string.substring(0, 1).toUpperCase())
                .append(string.substring(1))
                .append(") ")
                .append(getFullRubModification(dataModel))
                .append(dataModel.getCoin())
                .append(" ")
                .append(getCoinTail(dataModel))
                .toString();
    }

    private String getResponseV11(DataModel dataModel) {
        var stringBuilder = new StringBuilder();
        var converter = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        var dataModelOfSumOfVat = formDataDtoConverter.formDataDtoToDataModel(formDataDtoBuilder.getFromData(dataModel));

        return stringBuilder
                .append(dataModel.getTotal().toString().replace(".", dataModel.getPoint()))
                .append(" (")
                .append(cutTailOfString(converter.asWords(dataModel.getSumWithoutCoin()), TAIL_CUTTER_SIZE + 1))
                .append(") ")
                .append(getFullRubModification(dataModel))
                .append(dataModel.getCoin())
                .append(" ")
                .append(getCoinTail(dataModel))
                .append(", ")
                .append("включая ")
                .append(currencyCollection.getKindOfVat().get(dataModel.getVat()))
                .append(" (")
                .append(dataModel.getVat())
                .append("%) ")
                .append("в сумме ")
                .append(dataModel.getSumOfVat().toString().replace(".", dataModel.getPoint()))
                .append(" руб. (")
                .append(cutTailOfString(converter.asWords(dataModelOfSumOfVat.getSumWithoutCoin()), TAIL_CUTTER_SIZE + 1))
                .append(") ")
                .append(getFullRubModification(dataModelOfSumOfVat))
                .append(dataModelOfSumOfVat.getCoin())
                .append(" ")
                .append(getCoinTail(dataModelOfSumOfVat))
                .toString();
    }


    private String getRublePart(DataModel dataModel) {

        var converters = currencyCollection.getCurrencyMap().get(dataModel.getCurrency());
        var stringBuilder = new StringBuilder();
        return stringBuilder
                .append(cutTailOfString(converters.asWords(dataModel.getSumWithoutCoin()), TAIL_CUTTER_SIZE))
                .append(getFullRubModification(dataModel))
                .toString();
    }


    private StringBuilder cutTailOfString(String str, int endSize) {
        StringBuilder result = new StringBuilder(str.substring(0, str.length() - endSize));
        return result;
    }

    private String getFullRubModification(DataModel dataModel){
        String[] str = dataModel.getSumWithoutCoin().toString().split("");
        int i = Integer.parseInt(str[str.length - 1]);

        if (i == 1) {
            return "рубль ";
        } else if (i > 1 && i < 5) {
            return "рубля ";
        } else {
            return "рублей ";
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
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), TAIL_CUTTER_SIZE));
            stringBuilder.append("одна ");
        } else if (mas[1].equals("2") && !mas[0].equals("1")){
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), TAIL_CUTTER_SIZE));
            stringBuilder.append("две ");
        } else if ((mas[1].equals("3") || mas[1].equals("4")) && !mas[0].equals("1")) {
            if (!mas[0].equals("0")) stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[0] + "0"))), TAIL_CUTTER_SIZE));
            stringBuilder.append(cutTailOfString(converters.asWords(BigDecimal.valueOf(Integer.parseInt(mas[1]))), TAIL_CUTTER_SIZE));
        } else {
            stringBuilder.append(cutTailOfString(converters.asWords(dataModel.getCoin()), TAIL_CUTTER_SIZE));
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
