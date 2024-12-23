package com.ltc.turbobot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

    @JsonProperty("update_id")
    public int updateId;
    public Message message;
}
