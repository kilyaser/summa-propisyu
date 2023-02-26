package com.arcadag.summapropisyu.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.finance.tradukisto.MoneyConverters;

@RestController
@RequestMapping("api/v1")
public class MoneyController {

    @GetMapping("/convert")
    public ResponseEntity getConvertMoney() {
        MoneyConverters converters = MoneyConverters.RUSSIAN_BANKING_MONEY_VALUE;
        return null;
    }
}
