//package com.ltc.turbobot.controller;
//
//import com.ltc.turbobot.dto.request.AIRequestDto;
//import com.ltc.turbobot.service.AIService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/ai")
//@RequiredArgsConstructor
//public class AIController {
//    private final AIService service;
//
//    @PostMapping("/checkAnnouncement")
//    public ResponseEntity<?> getAnnouncement(@RequestBody AIRequestDto dto) {
//        try {
//            return ResponseEntity.status(HttpStatus.CREATED).body(service.processUserRequest(dto));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Something went wrong while processing the request: " + e.getMessage());
//        }
//    }
//}
