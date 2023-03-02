package com.arcadag.summapropisyu.converter;

import com.arcadag.summapropisyu.domain.DataModel;
import com.arcadag.summapropisyu.dtos.FormDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
@Slf4j
public class FormDataDtoConverter {

    public DataModel formDataDtoToDataModel(FormDataDto dto) {
        DataModel dataModel = new DataModel();
        String[] sumAndCoin = dto.getSum().split("\\.");
        dataModel.setSumWithoutCoin(BigDecimal.valueOf(Long.parseLong(sumAndCoin[0])));

        if (sumAndCoin.length == 2) {
            if (sumAndCoin[1].length() > 2) {
                sumAndCoin[1] = sumAndCoin[1].substring(0, 2);
            }
            if (sumAndCoin[1].length() == 1) {
                sumAndCoin[1] = sumAndCoin[1] + "0";
            }
            dataModel.setCoin(BigDecimal.valueOf(Long.parseLong(sumAndCoin[1])));
        } else {
            dataModel.setCoin(BigDecimal.ZERO);
        }

        dataModel.setTotal(BigDecimal.valueOf(Double.parseDouble(dto.getSum())));
        dataModel.setVat(dto.getInputVAT());
        dataModel.setSumOfVat(dataModel.getTotal()
                .divide(BigDecimal.valueOf(100 + dataModel.getVat()), 7, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(dataModel.getVat())).setScale(2, RoundingMode.HALF_UP));

        dataModel.setPoint(dto.getPoint());
        dataModel.setCurrency(dto.getCurrency());
        return dataModel;
    }
}
