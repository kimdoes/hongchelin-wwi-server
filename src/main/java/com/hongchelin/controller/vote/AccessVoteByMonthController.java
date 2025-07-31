/*
package com.hongchelin.controller.vote;

import com.hongchelin.Service.Vote.AccessVoteByMonthService;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/votes")
public class AccessVoteByMonthController {
    private final AccessVoteByMonthService accessVoteByMonthService;
    public AccessVoteByMonthController(AccessVoteByMonthService accessVoteByMonthService) {
        this.accessVoteByMonthService = accessVoteByMonthService;
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

*/