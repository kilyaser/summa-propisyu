package com.arcadag.summapropisyu.controller;

import com.arcadag.summapropisyu.dtos.AppResponseDto;
import com.arcadag.summapropisyu.dtos.FormDataDto;
import com.arcadag.summapropisyu.services.MoneyWritingService;
import com.arcadag.summapropisyu.validation.PatternValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class MoneyController {
    private final MoneyWritingService moneyWritingService;
    private final PatternValidator patternValidator;
    @PostMapping("/convert")
    public ResponseEntity<?> getConvertMoney(@RequestBody FormDataDto data) {
        log.info("++ MoneyController: {}", data);
        log.info("++ PatternValidator: {}", patternValidator.isMatches(data.getSum()));
        if (patternValidator.isMatches(data.getSum())) {
           return ResponseEntity.of(Optional.of(moneyWritingService.getMoneyWriting(data)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data");
        }

    }
}
