package com.hongchelin.controller.Store;

import com.hongchelin.service.Store.StoreRegisterService;
import com.hongchelin.dto.Request.StoreRegisterRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class StoreRegisterController {
    private final StoreRegisterService storeRegisterService;
    public StoreRegisterController(StoreRegisterService storeRegisterService) {
        this.storeRegisterService = storeRegisterService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> StoreRegister(
            @RequestBody @Valid StoreRegisterRequstDTO storeRegisterRequstDTO,
            HttpServletRequest request,
            @Value("${spring.jwt.secret}") String secret
    ){
        return storeRegisterService.storeRegiester(storeRegisterRequstDTO, request, secret);
    }
}