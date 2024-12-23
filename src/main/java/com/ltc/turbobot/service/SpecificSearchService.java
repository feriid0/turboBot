package com.ltc.turbobot.service;

import com.ltc.turbobot.client.TelegramFeignClients;
import com.ltc.turbobot.dto.request.SpecificRequestDto;
import com.ltc.turbobot.dto.response.SpecificResponseDto;
import com.ltc.turbobot.entity.SpecificSearch;
import com.ltc.turbobot.repository.SpecificSearchRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecificSearchService {
    private final SpecificSearchRepository specificSearchRepository;
    private final ModelMapper modelMapper;
    private final TelegramFeignClients telegramFeignClients;

    public String saveSpecific(SpecificRequestDto specificRequestDto) {
        SpecificSearch specificSearch = modelMapper.map(specificRequestDto, SpecificSearch.class);
        specificSearchRepository.save(specificSearch);
        return "Specific search saved";
    }

    public String deleteSpecific(Long chatId) {
        specificSearchRepository.deleteById(chatId);
        return "Specific search deleted";
    }

    public List<SpecificResponseDto> getSpecificDetails() {
        List<SpecificSearch> specificSearchList = specificSearchRepository.findAll();
        return specificSearchList.stream()
                .map(specificSearch -> modelMapper.map(specificSearch, SpecificResponseDto.class)).toList();
    }

    @Scheduled(fixedDelay = 60000)
    public void getUpdates() throws IOException {
        List<SpecificSearch> specificSearchList = specificSearchRepository.findAll();

        for (SpecificSearch specificSearch : specificSearchList) {
            Document doc = Jsoup.connect(specificSearch.getSpecificUrl()).get();
            Element price = doc.getElementsByClass("product-price").first();
            assert price != null;
            String priceText = price.text();
            String cleanedPrice = priceText.replaceAll("[^0-9]", "");
            int priceInt = Integer.parseInt(cleanedPrice);

            if (priceInt < specificSearch.getCurrentPrice()) {
                telegramFeignClients.sendMessage(specificSearch.getChatId(),
                        "Qiymet endirildi: " + specificSearch.getSpecificUrl());
            }
        }
    }


}
