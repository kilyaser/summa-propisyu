package com.arcadag.summapropisyu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DataModel {
    private BigDecimal total;

    private BigDecimal sumWithoutCoin;
    private BigDecimal vat;
    private BigDecimal coin;
    private int point;
    private String currency;
}
