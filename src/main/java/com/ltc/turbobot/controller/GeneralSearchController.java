package com.ltc.turbobot.controller;

import com.ltc.turbobot.dto.request.GeneralRequestDto;
import com.ltc.turbobot.dto.response.GeneralResponseDto;
import com.ltc.turbobot.service.GeneralSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general")
public class GeneralSearchController {
    private final GeneralSearchService generalSearchService;

    @PostMapping("saveSearchDetails")
    public String saveSearchDetails(@RequestBody GeneralRequestDto generalRequestDto) {
        return generalSearchService.saveSearchDetails(generalRequestDto);
    }

    @DeleteMapping("deleteSearchDetails")
    public String deleteSearchDetails(@RequestParam Long chatId) {
        return generalSearchService.deleteSearchDetails(chatId);
    }

    @GetMapping("getSearchDetails")
    public List<GeneralResponseDto> getSearchDetails() {
        return generalSearchService.getSearchDetails();
    }

//    @GetMapping("getFilters")
//    public void getFilters() throws IOException {
//        generalSearchService.getFilters();
//    }
//
//    @GetMapping("sendAveragePrice")
//    public void sendAveragePrice() throws IOException {
//        generalSearchService.sendAveragePrice();
//    }

}
