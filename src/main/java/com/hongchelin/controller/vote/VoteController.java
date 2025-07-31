package com.hongchelin.controller.vote;

import com.hongchelin.Service.Vote.AccessVoteByMonthService;
import com.hongchelin.Service.Vote.StoreRegisterInVoteService;
import com.hongchelin.Service.Vote.VoteService;
import com.hongchelin.dto.Request.StoreRegisterInVoteRequestDTO;
import com.hongchelin.dto.Request.voteRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/votes")
@Controller
public class VoteController {
    private final VoteService voteService;
    private final AccessVoteByMonthService accessVoteByMonthService;
    private final StoreRegisterInVoteService storeRegisterInVoteService;
    public VoteController(VoteService voteService,
                          AccessVoteByMonthService accessVoteByMonthService,
                          StoreRegisterInVoteService storeRegisterInVoteService) {
        this.voteService = voteService;
        this.accessVoteByMonthService = accessVoteByMonthService;
        this.storeRegisterInVoteService = storeRegisterInVoteService;
    }

    @PostMapping()
    public ResponseEntity<ResponseDTO> VTController( @RequestBody voteRequstDTO voteRequestDTO, HttpServletRequest request, @Value("${spring.jwt.secret}") String secret) {
        return voteService.VoteService(secret, request, voteRequestDTO);
    }

    @PostMapping("/stores")
    public ResponseEntity<ResponseDTO> registerStore(
            @RequestBody @Valid StoreRegisterInVoteRequestDTO storeRegisterInVoteRequestDTO) {
        return storeRegisterInVoteService.storeRegisterInVote(storeRegisterInVoteRequestDTO);
    }

    @GetMapping("/candidates")
    public ResponseEntity<ResponseDTO> AccessVoteByMonth(@RequestParam String month) {
        if (month != null) {
            Integer year = Integer.parseInt(month.split("-")[0]);
            Integer MonthInteger = Integer.parseInt(month.split("-")[1]);
            return accessVoteByMonthService.AccessVoteByMonth(year, MonthInteger);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(500)
                    .message("Internal Server Error")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDTO);
        }
    }
}
