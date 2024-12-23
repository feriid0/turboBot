package com.ltc.turbobot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    @JsonProperty("message_id")
    public int messageId;
    public From from;
    public Chat chat;
    public int date;
    public String text;

}
