package com.hongchelin.controller.vote;

import com.hongchelin.Service.Vote.VoteService;
import com.hongchelin.dto.Request.voteRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class VoteController {
    private final VoteService voteService;
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    //@PostMapping("/votes")
    @GetMapping("/votes")
    public ResponseEntity<ResponseDTO> VTController( /*@RequestBody voteRequstDTO voteRequestDTO, HttpServletRequest request,*/ @Value("${spring.jwt.secret}") String secret) {
        return voteService.VoteService(secret/*, request, voteRequestDTO*/);
    }
}
