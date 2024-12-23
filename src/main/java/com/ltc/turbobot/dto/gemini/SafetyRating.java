package com.ltc.turbobot.dto.gemini;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafetyRating {
    private String category;
    private String probability;

    @Override
    public String toString() {
        return "SafetyRating{" +
                "category='" + category + '\'' +
                ", probability='" + probability + '\'' +
                '}';
    }
}
