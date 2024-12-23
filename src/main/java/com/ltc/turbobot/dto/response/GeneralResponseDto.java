package com.ltc.turbobot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GeneralResponseDto {

    private String generalUrl;

    private String keyword;

    private Date searchDate;


}
