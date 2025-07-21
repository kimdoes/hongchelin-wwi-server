package com.hongchelin.Service.Vote;

import com.hongchelin.dto.StoreDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccessVoteByMonthService {
    public ResponseEntity<ResponseDTO> AccessVoteByMonth(Integer year, Integer month) {
        System.out.println("연도 : "+year + " " + month);
        //DB에서 가져와 StoreDTO에 저장할 코드 필요
        StoreDTO storeDTO = StoreDTO.builder()
                .storeName("마치식당")
                .storeInfoOneLine("윤마치가 낋여주는 맛있는 노래")
                .identifier("03280328")
                .votedAmount(328)
                .build();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(200)
                .message("성공")
                .StoreInfo(storeDTO)
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
