package com.arcadag.summapropisyu.validation;

import com.arcadag.summapropisyu.dtos.FormDataDto;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatternValidator {
    private final Pattern digitPattern = Pattern.compile("\\d+|\\d+\\.|\\d+\\.\\d+|\\d+,|\\d+,\\d+");

    public boolean isMatches(FormDataDto dto) {
        if (!"RUB".equals(dto.getCurrency())) return false;
        Matcher matcher = digitPattern.matcher(dto.getSum());
        return matcher.matches();
    }
}
