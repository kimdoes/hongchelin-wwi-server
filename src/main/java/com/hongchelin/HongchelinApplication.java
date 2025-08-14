package com.hongchelin;

import com.hongchelin.Domain.Member;
import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.Repository.StoreRepositoryInterface;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableTransactionManagement
public class HongchelinApplication {
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
				.storeInfoOneline("바다에 들어가지 마세요!")
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
}
