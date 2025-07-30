package com.hongchelin.Service.Store;

import com.hongchelin.Domain.Store;
import com.hongchelin.Repository.StoreRepository;
import com.hongchelin.dto.Request.StoreRegisterRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreRegisterService {
    private final StoreRepository storeRepository;
    public StoreRegisterService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public ResponseEntity<ResponseDTO> storeRegiester(StoreRegisterRequstDTO storeRegisterRequstDTO) {
        String storeName = storeRegisterRequstDTO.getStoreName();
        String storeLocationInMapX = storeRegisterRequstDTO.getStoreLocationInMapX();
        String storeLocationInMapY = storeRegisterRequstDTO.getStoreLocationInMapY();
        String storeImg = storeRegisterRequstDTO.getStoreImg();
        String storeLocation = storeRegisterRequstDTO.getStoreLocation();

        Store store = Store.builder()
                .storeName(storeName)
                .storeLocationInMapX(storeLocationInMapX)
                .storeLocationInMapY(storeLocationInMapY)
                .storeImg(storeImg)
                .storeLocation(storeLocation)
                .build();

        boolean isInNow = storeRepository.checker(store);

        if (isInNow) { //중복안됨
            storeRepository.save(store);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .build();

            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("이미 등록된 점포입니다.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }
}
