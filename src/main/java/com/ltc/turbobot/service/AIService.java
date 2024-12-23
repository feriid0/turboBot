//package com.ltc.turbobot.service;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//import com.ltc.turbobot.client.GeminiAPIClient;
//import com.ltc.turbobot.client.TelegramFeignClients;
//import com.ltc.turbobot.dto.gemini.Candidate;
//import com.ltc.turbobot.dto.gemini.Root;
//import com.ltc.turbobot.dto.request.AIRequestDto;
//import com.ltc.turbobot.dto.response.AIResponseDto;
//import com.ltc.turbobot.entity.AIEntity;
//import com.ltc.turbobot.repository.AIRepository;
//import com.ltc.turbobot.repository.GeneralSearchRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class AIService extends GeneralSearchService implements GeminiService {
//    private final AIRepository repository;
//    private final GeminiAPIClient client;
//    private final ModelMapper modelMapper;
//
//    @Value("${gemini.api.key}")
//    private String key;
//    private Root latestUpdates;
//
//    public AIService(GeneralSearchRepository generalSearchRepository,
//                     ModelMapper modelMapper,
//                     TelegramFeignClients telegramFeignClients,
//                     AIRepository repository,
//                     GeminiAPIClient client,
//                     ModelMapper modelMapper1) {
//        super(generalSearchRepository, modelMapper, telegramFeignClients);
//        this.repository = repository;
//        this.client = client;
//        this.modelMapper = modelMapper1;
//    }
//
//    @Override
//    public Root processUserRequest(AIRequestDto dto) {
//        AIEntity entity = modelMapper.map(dto, AIEntity.class);
//
//        String instruction = constructInstruction(dto);
//        Root updates = getUpdates(instruction);
//        String extractedText = extractedTextFromGeminiResponse(updates);
//        dto.setGeminiResponse(extractedText);
//
//        log.info("AI request received.");
//        latestUpdates = updates;
//        repository.save(entity);
//        return latestUpdates;
//    }
//
//    @Override
//    public Root getLatestResponse() {
//        return latestUpdates;
//    }
//
//    private String constructInstruction(AIRequestDto dto) {
//        StringBuilder constructor = new StringBuilder();
//        constructor.append("Prompt Text ").append(dto.getText()).append("\n");
//        constructor.append("You will get prompt from user for the https://turbo.az/ (")
//                .append(dto.getText()).append("). You will check the announcements if they contain these" +
//                        " features or not. Then find and give these announcements to the user. You will get data" +
//                        " of https://turbo.az/ from here:").append(getSearchDetails())
//                .append(". Based on these, you will check and provide the links of the cars to the user. You will" +
//                        "r get the prompt texts in Azerbaijani language, Russian language.");
//        return constructor.toString();
//    }
//
//    private Root getUpdates(String instruction) {
//        try {
//            JsonObject json = new JsonObject();
//            JsonArray partsArray = new JsonArray();
//            JsonObject partsObject = new JsonObject();
//            JsonObject contentsObject = new JsonObject();
//            JsonArray contentsArray = new JsonArray();
//
//            partsObject.add("text", new JsonPrimitive(instruction));
//            partsArray.add(partsObject);
//            contentsObject.add("parts", partsArray);
//            contentsArray.add(contentsObject);
//            json.add("contents", contentsArray);
//
//            String content = json.toString();
//            return client.getData(key, content);
//        } catch (Exception e) {
//            log.error("Error while getting response from Gemini AI: }", e);
//            throw e;
//        }
//    }
//
//    private String extractedTextFromGeminiResponse(Root updates) {
//        StringBuilder textBuilder = new StringBuilder();
//
//        if (updates.getCandidates() != null) {
//            for (Candidate candidate : updates.getCandidates()) {
//                String text = candidate.getContent().getParts().get(0).getText();
//                text = text.replace("*", "");
//                textBuilder.append(text).append("\n\n");
//            }
//        }
//
//        String response = textBuilder.toString().trim();
//
//        return response
//                .replaceAll("(?i)\\bPrompt Text:\\b", "\nPrompt Text:\n");
//    }
//
//    @Transactional(readOnly = true)
//    public List<AIResponseDto> readAll() {
//        log.info("All AI info responded.");
//        return repository
//                .findAll()
//                .stream()
//                .map(m -> modelMapper.map(m, AIResponseDto.class))
//                .toList();
//    }
//}
