package com.arcadag.summapropisyu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DataModel {
    private BigDecimal total;

    private BigDecimal sumWithoutCoin;
    private Integer vat;
    private BigDecimal sumOfVat;
    private BigDecimal coin;
    private String point;
    private String currency;
}
