package com.ltc.turbobot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AIRequestDto {
    private String text;
    private String geminiResponse;
}
