// infra/DataInitializer.java
package com.hongchelin.mypage.infra;

import com.hongchelin.mypage.entity.Badge;
import com.hongchelin.mypage.repository.BadgeRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    ApplicationRunner seedBadges(BadgeRepository badges) {
        return args -> {
            ensure(badges, "WELCOME", "환영 배지", "가입 환영!", "/files/badges/welcome.png");
            ensure(badges, "KOREAN",  "한식",     "한식 마스터", "/files/badges/korean.png");
            ensure(badges, "JAPANESE","일식",     "일식 마스터", "/files/badges/japanese.png");
            ensure(badges, "WESTERN", "양식",     "양식 마스터", "/files/badges/western.png");
            ensure(badges, "CHINESE", "중식",     "중식 마스터", "/files/badges/chinese.png");
            ensure(badges, "CAFE",    "카페",     "카페 마스터", "/files/badges/cafe.png");
        };
    }

    private void ensure(BadgeRepository repo, String code, String name, String desc, String icon) {
        repo.findByCode(code).orElseGet(() -> repo.save(Badge.create(code, name, desc, icon)));
    }
}
