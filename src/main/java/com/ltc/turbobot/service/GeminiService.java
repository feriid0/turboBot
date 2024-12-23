package com.ltc.turbobot.service;

import com.ltc.turbobot.dto.gemini.Root;
import com.ltc.turbobot.dto.request.AIRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    Root processUserRequest(AIRequestDto dto);

    Root getLatestResponse();
}
