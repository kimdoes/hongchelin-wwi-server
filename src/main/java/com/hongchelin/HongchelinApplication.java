package com.hongchelin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongchelin.Domain.Member;
import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.Repository.StoreRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableTransactionManagement
@Component

public class HongchelinApplication {
	@Value("${spring.jwt.kakaoSecret}")
	private String kakaoSecret;

	List<JsonNode> documentList = new ArrayList<>();

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final StoreRepositoryInterface storeRepositoryInterface;
	private final StoreForVoteRepositoryInterface storeForVoteRepositoryInterface;
	private final MemberRepositoryInterface memberRepositoryInterface;

	public HongchelinApplication(StoreRepositoryInterface storeRepositoryInterface, StoreForVoteRepositoryInterface storeForVoteRepositoryInterface, MemberRepositoryInterface memberRepositoryInterface) {
		this.storeRepositoryInterface = storeRepositoryInterface;
		this.storeForVoteRepositoryInterface = storeForVoteRepositoryInterface;
		this.memberRepositoryInterface = memberRepositoryInterface;
	}

	public static void main(String[] args) {
		System.out.println("HongchelinApplication");
		SpringApplication.run(HongchelinApplication.class, args);
	}

/*
	@PostConstruct
	public void storeForVoteDbSetting() throws Exception {
		List<Integer> idList = List.of(3630, 3640, 3648, 3661, 3670);

		for (Integer id : idList) {
			Integer votedCount = (int)(Math.random() * 1000);

			StoreForVote storeForVote = StoreForVote.builder()
					.storeId(id)
					.votedCount(votedCount)
					.build();

			storeForVoteRepositoryInterface.save(storeForVote);
		}
	}
 */

	/*
	@PostConstruct
	public void storeDBSetting() throws Exception {

		int pageCount = 1;
		int maxPage;
		List<Store> storeList = new ArrayList<>();



		WebClient webClient = WebClient.builder()
				.baseUrl("https://dapi.kakao.com")
				.defaultHeader("Authorization", "KakaoAK " + kakaoSecret)
				.build();

		String testResponse = webClient.get()
				.uri("/v2/local/search/category.json?category_group_code=FD6&x=126.9250355422&y=37.5525245968145&radius=1300")
				.retrieve()
				.bodyToMono(String.class)
				.block();

		JsonNode jsonNode = objectMapper.readTree(testResponse).get("meta");
		int totalCount = jsonNode.get("total_count").asInt();
		int pageableCount = jsonNode.get("pageable_count").asInt();

		maxPage = Math.min((int) Math.ceil((double) totalCount / pageableCount), 45);


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

				boolean isInStore = storeList.contains(store);
				System.out.println(isInStore + " " + storeName);

				if (!isInStore) {
					storeList.add(store);
				} else {
					System.out.println("여길 통과함");
				}
			}
		}

		storeRepositoryInterface.saveAll(storeList);
	}
	*/


/*
	@PostConstruct
	public void test() {
		Member adminMember1 = Member.builder()
				.userId("admin")
				.password("admin1234**")
				.nickname("관리자")
				.role("Admin")
				.voteAvailable(false)
				.email("hongchelin422@gmail.com")
				.build();

		memberRepositoryInterface.save(adminMember1);

		Store store1 = Store.builder()
				.storeName("네이버")
				.storeLocation("경기도 성남시 분당구 정자일로 95")
				.storeImg("이미지경로")
				.storeInfoOneline("네이버1784")
				.build();

		storeRepositoryInterface.save(store1);

		StoreForVote sf1 = StoreForVote.builder()
				.storeId(store1.getId())
				.votedCount(331)
				.build();

		Store store2 = Store.builder()
				.storeName("홍익홍익")
				.storeLocation("서울특별시 마포구 와우산로 94")
				.storeImg("이미지경로")
				.storeInfoOneline("홍문관")
				.build();

		storeRepositoryInterface.save(store2);

		StoreForVote sf2 = StoreForVote.builder()
				.storeId(store2.getId())
				.votedCount(211)
				.build();

		Store store3 = Store.builder()
				.storeName("잠실야구장")
				.storeLocation("서울특별시 송파구 올림픽로 19-2")
				.storeImg("이미지경로")
				.storeInfoOneline("잠실야구장입니다.")
				.build();

		Store store4 = Store.builder()
				.storeName("서 울 역")
				.storeLocation("서울특별시 용산구 한강대로 405")
				.storeImg("이미지경로")
				.storeInfoOneline("서울역입니다.")
				.build();

		storeRepositoryInterface.save(store3);
		storeRepositoryInterface.save(store4);

		StoreForVote sf3 = StoreForVote.builder()
				.storeId(store4.getId())
				.votedCount(216)
				.build();

		Store store5 = Store.builder()
				.storeName("궁평항")
				.storeLocation("경기도 화성시 서신면 궁평항로 1049-24")
				.storeImg("이미지경로")
				.storeInfoOneline("궁평항입니다")
				.build();

		storeRepositoryInterface.save(store5);

		StoreForVote sf4 = StoreForVote.builder()
				.storeId(store5.getId())
				.votedCount(119)
				.build();

		storeForVoteRepositoryInterface.save(sf1);
		storeForVoteRepositoryInterface.save(sf2);
		storeForVoteRepositoryInterface.save(sf3);
		storeForVoteRepositoryInterface.save(sf4);
	}

	 */
}
