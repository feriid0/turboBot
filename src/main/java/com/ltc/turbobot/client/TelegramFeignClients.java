package com.ltc.turbobot.client;

import com.ltc.turbobot.dto.telegram.TelegramDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegram-service" , url = "https://api.telegram.org/bot7036182536:AAHdFrMXyZZu-qPvbabIGSFn8-lG7ZchtB0")
public interface TelegramFeignClients {

    @GetMapping("/getUpdates")
    TelegramDto getUpdates(@RequestParam(name = "offset") Long offset);

    @PostMapping("/sendMessage")
    void sendMessage(@RequestParam(name = "chat_id") Long chatId,
                     @RequestParam(name = "text") String text);

}
