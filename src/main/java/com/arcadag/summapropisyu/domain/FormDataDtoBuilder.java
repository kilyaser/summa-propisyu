package com.arcadag.summapropisyu.domain;

import com.arcadag.summapropisyu.dtos.FormDataDto;
import org.springframework.stereotype.Component;

@Component
public class FormDataDtoBuilder {

    private FormDataDto formDataDto;


    public FormDataDto getFromData(DataModel dataModel) {
        this.formDataDto = new FormDataDto();
        formDataDto.setCurrency(dataModel.getCurrency());
        if (".".equals(dataModel.getPoint())) {
            formDataDto.setPoint(0);
        } else {
            formDataDto.setPoint(1);
        }
        formDataDto.setInputVAT(dataModel.getVat());
        formDataDto.setSum(dataModel.getSumOfVat().toString());
        return formDataDto;

    }



}
