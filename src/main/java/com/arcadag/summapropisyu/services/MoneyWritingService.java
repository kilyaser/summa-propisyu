package com.arcadag.summapropisyu.services;

import com.arcadag.summapropisyu.converter.FormDataDtoConverter;
import com.arcadag.summapropisyu.domain.AppResponseBuilder;
import com.arcadag.summapropisyu.domain.DataModel;
import com.arcadag.summapropisyu.dtos.AppResponseDto;
import com.arcadag.summapropisyu.dtos.FormDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MoneyWritingService {

    private final FormDataDtoConverter formDataDtoConverter;

    private final AppResponseBuilder builder;
    public AppResponseDto getMoneyWriting(FormDataDto dto) {
        DataModel dataModel = formDataDtoConverter.formDataDtoToDataModel(dto);

        return builder.create()
                .addResponses(dataModel)
                .getAppResponses();

    }

}
