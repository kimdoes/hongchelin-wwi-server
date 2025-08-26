package com.hongchelin.service.Vote;

import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.Repository.StoreRepositoryInterface;
import com.hongchelin.dto.Request.StoreRegisterInVoteRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreRegisterInVoteService {
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    private final StoreRepositoryInterface storeRepository;
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepositoryInterface;

    public StoreRegisterInVoteService(
            StoreForVoteRepositoryInterface storeForVoteRepository,
            StoreRepositoryInterface storeRepository,
            JWTFilter jwtFilter, MemberRepositoryInterface memberRepositoryInterface) {
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeRepository = storeRepository;
        this.jwtFilter = jwtFilter;
        this.memberRepositoryInterface = memberRepositoryInterface;
    }

    public ResponseEntity<ResponseDTO> storeRegisterInVote(
            StoreRegisterInVoteRequestDTO storeRegisterInVoteRequestDTO,
            HttpServletRequest request,
            String secret
    ) {
        try{
            String userId = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo().getIdentifier();
            String role = memberRepositoryInterface.findByUserId(userId).get(0).getRole();

            System.out.println(role);

            if (!role.equals("Admin")){
                throw new UnauthorizedException();
            }

            String location = storeRegisterInVoteRequestDTO.getStoreLocation();
            String storeName = storeRegisterInVoteRequestDTO.getStoreName();

            boolean isInDb = storeRepository.existsByStoreNameAndStoreLocation(storeName, location);

            if (!isInDb) {
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(400)
                        .message("가게 정보가 없습니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseDTO);
            }

            long storeId = storeRepository.findByStoreNameAndStoreLocation(storeName, location).getId();
            Integer count = storeForVoteRepository.countByStoreId(storeId);

            if (count == 0) {
                Store store = storeRepository.findByStoreNameAndStoreLocation(storeName, location);
                StoreForVote storeForVote = StoreForVote.builder()
                        .storeId(store.getId())
                        .votedCount(0)
                        .build();

                storeForVoteRepository.save(storeForVote);

                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(200)
                        .message("성공")
                        .build();

                return ResponseEntity.ok(responseDTO);

            } else {
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(500)
                        .message("이미 등록된 가게입니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(responseDTO);
            }
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }
}
