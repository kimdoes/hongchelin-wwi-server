package com.hongchelin.controller.Store;

import com.hongchelin.Service.Store.StoreRegisterService;
import com.hongchelin.dto.Request.StoreRegisterRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
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
    public ResponseEntity<ResponseDTO> StoreRegister(@RequestBody StoreRegisterRequstDTO storeRegisterRequstDTO){
        return storeRegisterService.storeRegiester(storeRegisterRequstDTO);
    }
}
