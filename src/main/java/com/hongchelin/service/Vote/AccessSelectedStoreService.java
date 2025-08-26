package com.hongchelin.service.Vote;

import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccessSelectedStoreService {
    public ResponseEntity<ResponseDTO> accessSelectedSotreService(String yearmonth) {
        if (yearmonth != null) {
            return null; //정보 불러오기
        } else {
            return null; //오류
        }
    }
}
