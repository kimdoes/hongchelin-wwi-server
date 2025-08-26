/*
package com.hongchelin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongchelin.Domain.Store;
import com.hongchelin.Repository.StoreRepositoryInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterStoreService {
    private final StoreRepositoryInterface storeRepositoryInterface;
    @Value("${spring.jwt.kakaotestSecret}")
    private String kakaoTestSecret;

    ObjectMapper objectMapper = new ObjectMapper();

    public RegisterStoreService(StoreRepositoryInterface storeRepositoryInterface) {
        this.storeRepositoryInterface = storeRepositoryInterface;
    }

    @PostConstruct
    public void storeDBSetting() throws Exception {
        int pageCount = 1;
        int maxPage;
        List<Store> storeList = new ArrayList<>();


        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoTestSecret)
                .build();

        List<String> locationList = List.of(
                "마포구신촌로12",
                "마포구동교로34길3",
                "마포구와우산로38길28",
                "마포구서강로75-16",
                "마포구독막로20길42",
                "마포구와우산로27",
                "마포구독막로57",
                "마포구어울마당로5길47",
                "마포구양화로10길15",
                "마포구잔다리로7안길4",
                "마포구동교로144",
                "마포구동교로153-6",
                "마포구월드컵북로4길43",
                "마포구와우산로23길20-27",
                "마포구와우산로21길28-6"
                );

        List<String> radiusList = List.of(
                "170",
                "352",
                "330",
                "357",
                "343",
                "349",
                "332",
                "325",
                "302",
                "300",
                "321",
                "354",
                "352",
                "250",
                "250"
        );
    }

    public void registerSetting(List<String> locationList,
                                WebClient webClient) throws Exception {
                for (String location : locationList) {
                    String testResponse = webClient.get()
                            .uri("/v2/local/search/address.json?query"
                            + location)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

                    JsonNode jsonNode = objectMapper.readTree(testResponse).get("documents");
                    String xLocation = jsonNode.get("x").asText();
                }


    }

    public void getStoreSetting(WebClient webClient,
                                String xLocation,
                                String yLocation,
                                String radius
                                ) throws Exception {
        int pageCount = 1;
        List<JsonNode> documentList = new ArrayList<>();
        List<Store> storeList = new ArrayList<>();

        String testResponse = webClient.get()
                .uri("/v2/local/search/category.json?category_group_code=FD6&x="
                        + xLocation
                        + "&y="
                        + yLocation
                        + "&radius="
                        + radius)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode jsonNode = objectMapper.readTree(testResponse).get("meta");
        int totalCount = jsonNode.get("total_count").asInt();
        int pageableCount = jsonNode.get("pageable_count").asInt();

        int maxPage = Math.min((int) Math.ceil((double) totalCount / pageableCount), 45);

        while (pageCount <= maxPage) {
            String response = webClient.get()
                    .uri("/v2/local/search/category.json?category_group_code=FD6&x=126.9250355422&y=37.5525245968145&radius=1300&page=" + pageCount)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode rootNode = objectMapper.readTree(response);
            System.out.println(pageCount);

            JsonNode documents = rootNode.get("documents");

            documentList.add(documents);
            pageCount++;
        }


        for (JsonNode document : documentList) {
            for (JsonNode element : document) {
                String storeName = element.get("place_name").asText();
                String storeLocation = element.get("road_address_name").asText();

                Store store = Store.builder()
                        .storeName(storeName)
                        .storeLocation(storeLocation)
                        .build();

                System.out.println(store);

                storeList.add(store);
            }
        }

        storeRepositoryInterface.saveAll(storeList);
    }
}
*/