package com.arcadag.summapropisyu.domain;

import com.arcadag.summapropisyu.dtos.FormDataDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class FormDataDtoBuilder {

    private FormDataDto formDataDto;


    public FormDataDto getFromData(DataModel dataModel) {
        log.info(">> fromDataDtoBuilder dataModel: {}", dataModel);
        this.formDataDto = new FormDataDto();
        formDataDto.setCurrency(dataModel.getCurrency());
        if (".".equals(dataModel.getPoint())) {
            formDataDto.setPoint(0);
        } else {
            formDataDto.setPoint(1);
        }
        formDataDto.setInputVAT(dataModel.getVat());
        formDataDto.setSum(dataModel.getSumOfVat().toString());
        log.info(">> formDataDtoBuilder fromDataDto: {}", formDataDto);
        return formDataDto;

    }



}
