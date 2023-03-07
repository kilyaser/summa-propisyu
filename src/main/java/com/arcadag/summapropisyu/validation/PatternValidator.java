package com.arcadag.summapropisyu.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatternValidator {
    private final Pattern digitPattern = Pattern.compile("\\d+|\\d+\\.|\\d+\\.\\d+|\\d+,|\\d+,\\d+");

    public boolean isMatches(String sum) {
        Matcher matcher = digitPattern.matcher(sum);
        return matcher.matches();
    }
}
