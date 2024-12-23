package com.ltc.turbobot.controller;

import com.ltc.turbobot.dto.request.SpecificRequestDto;
import com.ltc.turbobot.dto.response.SpecificResponseDto;
import com.ltc.turbobot.service.SpecificSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("secific")
public class SpecificSearchController {
    private final SpecificSearchService specificSearchService;

    @PostMapping("saveSpecific")
    public String saveSpecific(@RequestBody SpecificRequestDto specificRequestDto) {
        return specificSearchService.saveSpecific(specificRequestDto);
    }

    @DeleteMapping("deleteSpecific/{chatId}")
    public String deleteSpecific(@PathVariable Long chatId) {
        return specificSearchService.deleteSpecific(chatId);
    }

    @GetMapping("getSpecificDetails")
    public List<SpecificResponseDto> getSpecificDetails() {
        return specificSearchService.getSpecificDetails();
    }

//    @GetMapping("getUpdates")
//    public void getUpdates() throws IOException {
//        specificSearchService.getUpdates();
//    }

}
