/*
package com.hongchelin.controller.login.HongikUniv;

import com.hongchelin.Service.login.HongikUniv.hongikUnivloginSuccessService;
import com.hongchelin.dto.user.ResponseDTO;
import com.nimbusds.openid.connect.sdk.federation.policy.operations.ValueOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class hongikUnivEmailContrastController {
    hongikUnivloginSuccessService hongikUnivloginSuccessService;
    private RedisTemplate<Object, Object> redisTemplate;

    public hongikUnivEmailContrastController(hongikUnivloginSuccessService hongikUnivloginSuccessService) {
        this.hongikUnivloginSuccessService = hongikUnivloginSuccessService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public hongikUnivEmailContrastController(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //@PostMapping("/api/login/4/email/password")
    @GetMapping("/api/login/4/email/password")
    public ResponseEntity<ResponseDTO>hongikUnivEmailContrastController(@Value("${spring.jwt.secret}") String secret /*@RequestBody Map<String, String> request*//*) {
/*
        Map<String, String> request = new HashMap<>();
        request.put("pwd", "hi");

        ValueOperation<String, String> valueOperation = redisTemplate.opsForValue();
        return hongikUnivloginSuccessService.checkpwd(request, secret);
    }

}
*/