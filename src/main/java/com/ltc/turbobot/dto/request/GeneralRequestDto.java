package com.ltc.turbobot.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class GeneralRequestDto {

    private Long chatId;

    private String generalUrl;

    private String keyword;

    private Date searchDate;
}
