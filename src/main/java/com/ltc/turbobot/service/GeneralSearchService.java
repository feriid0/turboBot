package com.ltc.turbobot.service;

import com.ltc.turbobot.client.TelegramFeignClients;
import com.ltc.turbobot.dto.request.GeneralRequestDto;
import com.ltc.turbobot.dto.response.GeneralResponseDto;
import com.ltc.turbobot.entity.GeneralSearch;
import com.ltc.turbobot.repository.GeneralSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralSearchService {
    private final GeneralSearchRepository generalSearchRepository;
    private final ModelMapper modelMapper;
    private final TelegramFeignClients telegramFeignClients;

    public String saveSearchDetails(GeneralRequestDto generalRequestDto) {
        GeneralSearch generalSearch = modelMapper.map(generalRequestDto, GeneralSearch.class);
        generalSearch.setSearchDate(LocalDateTime.now().minusMinutes(2));
        generalSearchRepository.save(generalSearch);
        return "Saved Successfully";
    }

    public String deleteSearchDetails(Long chatId) {
        generalSearchRepository.deleteById(chatId);
        return "Deleted Successfully";
    }


    public List<GeneralResponseDto> getSearchDetails() {
        List<GeneralSearch> generalSearches = generalSearchRepository.findAll();
        return generalSearches.stream().map(generalSearch -> modelMapper.map(generalSearch, GeneralResponseDto.class)).toList();
    }

//    public static boolean isTimeAfter(String time, LocalDateTime dateTime) {
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        LocalTime parseTime = LocalTime.parse(time, timeFormatter);
//        LocalTime timeFromDateTime = dateTime.toLocalTime();
//        return parseTime.isAfter(timeFromDateTime);
//    }

//    {
//        "chatId": 924131045,
//            "generalUrl": "string",
//            "keyword": "string",
//            "searchDate": "2024-10-19T08:27:58.791Z"
//    }



    @Scheduled(fixedDelay = 60000)
    public void getFilters() throws IOException {

        List<GeneralSearch> generalSearches = generalSearchRepository.findAll();


        for (GeneralSearch search : generalSearches) {
            Document doc = Jsoup.connect(search.getGeneralUrl()).get();
            log.info("SEARCH URL : {}", search.getGeneralUrl());
            Elements elements = doc.getElementsByClass("products-i");

            for (Element element : elements) {
                Element timeElement = element.getElementsByClass("products-i__datetime").first();
                assert timeElement != null;
                String timeText = timeElement.text();

                if (timeText.contains("bugün")) {
                    String second = timeText.split(",")[1].trim();
                    String result = second.split(" ")[1];
                    LocalTime carTime = LocalTime.parse(result).truncatedTo(ChronoUnit.MINUTES);  // Truncate carTime to minute precision
                    LocalTime now = LocalDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MINUTES);

                    if (result != null && carTime.isAfter(now.minusMinutes(2))
                            && carTime.isBefore(now)) {
                        Elements elementsAttribute = element.getElementsByAttribute("href");
                        String link = elementsAttribute.attr("abs:href");
                        telegramFeignClients.sendMessage(search.getChatId(), "Yeni elan: \n" + link);
                    }
                }
            }
        }
    }


//ISLEYEN KOD
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendAveragePrice() throws IOException {
        List<GeneralSearch> generalSearches = generalSearchRepository.findAll();

        for (GeneralSearch search : generalSearches) {
            log.info("General search : {}", search.getGeneralUrl());

            Document doc = Jsoup.connect(search.getGeneralUrl()).get();
            Elements elements = doc.getElementsByClass("products-i");
            int totalPrice = 0;
            int itemCount = 0;

            for (Element element : elements) {
                Element priceElement = element.getElementsByClass("product-price").first();
                assert priceElement != null;

                String priceText = priceElement.text();
                String cleanedPrice = priceText.replaceAll("[^0-9]", "");
                int price = Integer.parseInt(cleanedPrice);

                if (priceText.contains("$")){
                    price *= 1.7;
                }
                totalPrice += price;
                itemCount++;

            }
            double averagePrice = itemCount > 0 ? (double) totalPrice / itemCount : 0;
            int intAveragePrice = (int) averagePrice;
            long chatId = search.getChatId();
            String message = "Sizin axtarisiniza uygun avtomobillerin qiymet ortalamasi: " + intAveragePrice + "AZN";

            telegramFeignClients.sendMessage(chatId, message);
        }
    }
}




//ISLEYEN KOD       YENI ELAN
//private final Set<String> sentCarLinks = new HashSet<>();
//@Scheduled(fixedDelay = 5000)
//public void getFilters() throws IOException {
//    List<GeneralSearch> generalSearches = generalSearchRepository.findAll();
//
//    for (GeneralSearch search : generalSearches) {
//        Document doc = Jsoup.connect(search.getGeneralUrl()).get();
//        Elements elements = doc.getElementsByClass("products-i");
//
//        for (Element element : elements) {
//            Element timeElement = element.getElementsByClass("products-i__datetime").first();
//            assert timeElement != null;
//            String timeText = timeElement.text();
//
//            if (timeText.contains("bugün")){
//
//                Element linkElement = element.getElementsByTag("a").first();
//
//                if (linkElement != null) {
//                    String carLink = linkElement.attr("href");
//                    String baseUrl = "https://turbo.az";
//                    if (!sentCarLinks.contains(carLink)) {
//                        long chatId = search.getChatId();
//                        telegramFeignClients.sendMessage(chatId, "Yeni elan yuklendi: " + baseUrl + carLink);
//                        sentCarLinks.add(carLink);
//                    }
//                }
//            }
//        }
//    }
//}