package com.hongchelin.controller.vote;

import com.hongchelin.Service.Vote.AccessSelectedStoreService;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/votes")
public class AccessSelectedStoreController {
    private final AccessSelectedStoreService accessSelectedStoreService;
    public AccessSelectedStoreController(AccessSelectedStoreService accessSelectedStoreService) {
        this.accessSelectedStoreService = accessSelectedStoreService;
    }

    @GetMapping("/result")
    public ResponseEntity<ResponseDTO> ASSC(@RequestParam String month) {
        return accessSelectedStoreService.accessSelectedSotreService(month);
    }
}
