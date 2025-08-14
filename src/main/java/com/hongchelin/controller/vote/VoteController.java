package com.hongchelin.controller.vote;

import com.hongchelin.service.Vote.*;
import com.hongchelin.dto.Request.StoreRegisterInVoteRequestDTO;
import com.hongchelin.dto.Request.voteRequstDTO;
import com.hongchelin.dto.Response.PastStoreForVoteResponseDTO;
import com.hongchelin.dto.Response.StoreForVoteResponseDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/votes")
@Controller
public class VoteController {
    private final VoteReachService voteReachService;
    private final AccessVoteByMonthService accessVoteByMonthService;
    private final StoreRegisterInVoteService storeRegisterInVoteService;
    private final AccessSelectedStoreService accessSelectedStoreService;
    private final VoteService voteService;
    private final VoteResultService voteResultService;
    private final AccessMyVoteService accessMyVoteService;

    public VoteController(VoteReachService voteReachService,
                          AccessVoteByMonthService accessVoteByMonthService,
                          StoreRegisterInVoteService storeRegisterInVoteService,
                          AccessSelectedStoreService accessSelectedStoreService,
                          VoteService voteService,
                          VoteResultService voteResultService, AccessMyVoteService accessMyVoteService) {
        this.voteReachService = voteReachService;
        this.accessVoteByMonthService = accessVoteByMonthService;
        this.storeRegisterInVoteService = storeRegisterInVoteService;
        this.accessSelectedStoreService = accessSelectedStoreService;
        this.voteService = voteService;
        this.voteResultService = voteResultService;
        this.accessMyVoteService = accessMyVoteService;
    }

    @GetMapping()
    public ResponseEntity<StoreForVoteResponseDTO> VTController(
            HttpServletRequest request,
            @Value("${spring.jwt.secret}") String secret) {
        return voteReachService.VoteService(secret, request);
    }

    @PostMapping()
    public ResponseEntity<StoreForVoteResponseDTO> voteServiceConnection(
            HttpServletRequest request,
            @Value("${spring.jwt.secret}") String secret,
            @RequestBody voteRequstDTO voteRequstDTO) {
        return voteService.voteMainService(secret, voteRequstDTO, request);
    }

    @PostMapping("/stores")
    public ResponseEntity<ResponseDTO> registerStore(
            @RequestBody @Valid StoreRegisterInVoteRequestDTO storeRegisterInVoteRequestDTO,
            HttpServletRequest request,
            @Value("${spring.jwt.secret}") String secret) {
        return storeRegisterInVoteService.storeRegisterInVote(storeRegisterInVoteRequestDTO, request, secret);
    }


    @GetMapping("/candidates")
    public ResponseEntity<PastStoreForVoteResponseDTO> AccessVoteByMonth(
            @Value("${spring.jwt.secret}") String secret,
            HttpServletRequest request) {
        return accessMyVoteService.accessMyVoteService(request, secret);
    }

    @GetMapping("/result/month")
    public ResponseEntity<PastStoreForVoteResponseDTO> ASSC(@RequestParam String month) {
        return accessVoteByMonthService.AccessVoteByMonth(month);
    }

    @GetMapping("/result")
    public ResponseEntity<StoreForVoteResponseDTO> voteResultAccess(
            HttpServletRequest request,
            @Value("${spring.jwt.secret}") String secret
    ) {
        return voteResultService.voteResult();
    }
}
