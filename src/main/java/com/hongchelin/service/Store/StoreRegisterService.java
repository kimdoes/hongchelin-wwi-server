package com.hongchelin.service.Store;

import com.hongchelin.Domain.Store;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.StoreRepositoryInterface;
import com.hongchelin.dto.Request.StoreRegisterRequstDTO;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreRegisterService {
    private final StoreRepositoryInterface storeRepository;
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepositoryInterface;

    public StoreRegisterService(StoreRepositoryInterface storeRepository,
                                JWTFilter jwtFilter, MemberRepositoryInterface memberRepositoryInterface) {
        this.storeRepository = storeRepository;
        this.jwtFilter = jwtFilter;
        this.memberRepositoryInterface = memberRepositoryInterface;
    }

    public ResponseEntity<ResponseDTO> storeRegiester(StoreRegisterRequstDTO storeRegisterRequstDTO,
                                                      HttpServletRequest request,
                                                      String secret) {

        try{
            String userId = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo().getIdentifier();
            String role = memberRepositoryInterface.findByUserId(userId).get(0).getRole();

            if (!role.equals("Admin")){
                throw new UnauthorizedException();
            }

            String storeName = storeRegisterRequstDTO.getStoreName();
            String storeInfoOneline = storeRegisterRequstDTO.getStoreInfoOneline();
            String storeImg = storeRegisterRequstDTO.getStoreImg();
            String storeLocation = storeRegisterRequstDTO.getStoreLocation();

            Store store = Store.builder()
                    .storeName(storeName)
                    .storeInfoOneline(storeInfoOneline)
                    .storeImg(storeImg)
                    .storeLocation(storeLocation)
                    .build();

            Integer countInDB = storeRepository.countByStoreLocation(storeLocation);

            if (countInDB == 0) { //중복안됨
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
    } catch(Exception e){
        throw new UnauthorizedException();
        }
    }
}
