package com.arcadag.summapropisyu.controller;

import com.arcadag.summapropisyu.dtos.AppResponseDto;
import com.arcadag.summapropisyu.dtos.FormDataDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MoneyControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getConvertMoneyTest() {
        FormDataDto dto = getDto("1000.", 0, 20);
        AppResponseDto appResponseDto = webTestClient.post()
                .uri("/api/v1/convert")
                .bodyValue(dto)
                .exchange()
                .expectBody(AppResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(appResponseDto);
        Assertions.assertNotNull(appResponseDto.getResponses());
        Assertions.assertEquals(11, appResponseDto.getResponses().size());
    }

    @Test
    public void getConvertMoneyBadRequestTest() {
        FormDataDto dto = getDto("incorrectData", 0, 20);

        webTestClient.post()
                .uri("api/v1/convert")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private FormDataDto getDto(String sum, int point, int vat) {
        var dto = new FormDataDto();
        dto.setSum(sum);
        dto.setPoint(point);
        dto.setInputVAT(vat);
        dto.setCurrency("RUB");
        return dto;
    }
}
