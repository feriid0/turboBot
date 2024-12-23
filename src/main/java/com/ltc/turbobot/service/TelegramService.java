package com.ltc.turbobot.service;

import com.ltc.turbobot.client.TelegramFeignClients;
import com.ltc.turbobot.dto.telegram.Result;
import com.ltc.turbobot.dto.telegram.TelegramDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramFeignClients telegramFeignClients;
    private Long offset = 0L;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {

        for (Result r : telegramFeignClients.getUpdates(offset + 1).getResult()) {
            if (r.getMessage() != null && "/start".equals(r.getMessage().getText())) {
            long chatId = r.getMessage().getChat().id;
            telegramFeignClients.sendMessage(chatId,"Sizin identifikasiya nömrəniz : "+ chatId);
        }
            offset = (long) r.updateId;
        }

    }

}
