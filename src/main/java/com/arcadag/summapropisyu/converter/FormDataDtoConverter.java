package com.arcadag.summapropisyu.converter;

import com.arcadag.summapropisyu.domain.DataModel;
import com.arcadag.summapropisyu.dtos.FormDataDto;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;


@Component
public class FormDataDtoConverter {

    public DataModel formDataDtoToDataModel(FormDataDto dto) {
        DataModel dataModel = new DataModel();
        String[] sumAndCoin = dto.getSum().split("\\.");
        dataModel.setSumWithoutCoin(BigDecimal.valueOf(Long.parseLong(sumAndCoin[0])));
        dataModel.setCoin(BigDecimal.valueOf(Long.parseLong(sumAndCoin[1])));
        dataModel.setTotal(BigDecimal.valueOf(Float.parseFloat(dto.getSum())));
        dataModel.setVat(BigDecimal.valueOf(dto.getInputVAT()));
//        dataModel.setVat(BigDecimal.valueOf(Float.valueOf(dto.getSum())).setScale(2, RoundingMode.FLOOR)
//                        .divide(BigDecimal.valueOf(100))
//                        .multiply(BigDecimal.valueOf(dto.getInputVAT())));

        dataModel.setPoint(dto.getPoint());
        dataModel.setCurrency(dto.getCurrency());
        return dataModel;
    }
}
