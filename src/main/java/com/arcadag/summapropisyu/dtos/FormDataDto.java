package com.arcadag.summapropisyu.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormDataDto {
    private String currency;
    private int inputVAT;
    private int point;
    private String sum;
}
