package com.arcadag.summapropisyu.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AppResponseDto {
    private List<String> responses;
}
