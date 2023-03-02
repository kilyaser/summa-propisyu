package com.arcadag.summapropisyu.controller;


import com.arcadag.summapropisyu.dtos.FormDataDto;
import com.arcadag.summapropisyu.services.MoneyWritingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class MoneyController {
    private final MoneyWritingService moneyWritingService;
    @PostMapping("/convert")
    public FormDataDto getConvertMoney(@RequestBody FormDataDto data) {
        log.info("MoneyController {}", data);
        moneyWritingService.getMoneyWriting(data);
        return data;

    }
}
