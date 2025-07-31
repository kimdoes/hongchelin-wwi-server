package com.hongchelin.Service.Vote;

import com.hongchelin.Domain.Store;
import com.hongchelin.Repository.StoreForVoteRepository;
import com.hongchelin.Repository.StoreRepository;
import com.hongchelin.dto.Request.StoreRegisterInVoteRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreRegisterInVoteService {
    private final StoreForVoteRepository storeForVoteRepository;
    private final StoreRepository storeRepository;
    public StoreRegisterInVoteService(
            StoreForVoteRepository storeForVoteRepository,
            StoreRepository storeRepository) {
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeRepository = storeRepository;
    }

    public ResponseEntity<ResponseDTO> storeRegisterInVote(StoreRegisterInVoteRequestDTO storeRegisterInVoteRequestDTO) {
        String location = storeRegisterInVoteRequestDTO.getStoreLocation();

        boolean check = storeForVoteRepository.checker(location);

        if (check) {
            String storeName = storeRegisterInVoteRequestDTO.getStoreName();
            Store store = storeRepository.findByStoreName(storeName);

            storeForVoteRepository.save(store);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .build();

            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("가게 정보가 없습니다.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }
}
