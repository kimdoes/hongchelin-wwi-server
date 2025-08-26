package com.hongchelin.service;

import com.hongchelin.Domain.PastStoreForVote;
import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.StoreRepositoryInterface;
import com.hongchelin.dto.Response.StoreResponseDTO;
import com.hongchelin.dto.Response.StoreResponseEntityDTO;
import com.hongchelin.exceptions.CannotFoundDbElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreConverterService {
    private final StoreRepositoryInterface storeRepository;

    public StoreConverterService(StoreRepositoryInterface storeRepository) {
        this.storeRepository = storeRepository;
    }

    public ResponseEntity<StoreResponseEntityDTO> convert(List<StoreForVote> storeForVotes) {

        List<StoreResponseDTO> storesList = storeForVotes.stream()
                .map(storeForVote -> {
                        Store store = storeRepository.findById(storeForVote.getStoreId());

                        if (store == null) {
                            throw new CannotFoundDbElementException();
                        }

                        return StoreResponseDTO.builder()
                            .storeId(store.getId())
                            .storeName(store.getStoreName())
                            .storeLocation(store.getStoreLocation())
                            .storeInfoOneline(store.getStoreInfoOneline())
                            .storeImg(store.getStoreImg())
                            .votedCount(storeForVote.getVotedCount())
                            .build();
                })
                .sorted((s1, s2) -> s2.getVotedCount() - s1.getVotedCount())
                .collect(Collectors.toList());

        StoreResponseEntityDTO storeResponseEntityDTO = StoreResponseEntityDTO.builder()
                .status(200)
                .message("성공")
                .stores(storesList)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponseEntityDTO);
    }


    public ResponseEntity<StoreResponseEntityDTO> convertPast(List<PastStoreForVote> storeForVotes) {

        List<StoreResponseDTO> storesList = storeForVotes.stream()
                .map(pastStoreForVote -> {
                    Store store = storeRepository.findById(pastStoreForVote.getStoreId());

                    if (store == null) {
                        throw new CannotFoundDbElementException();
                    }

                    return StoreResponseDTO.builder()
                            .storeId(store.getId())
                            .storeName(store.getStoreName())
                            .storeLocation(store.getStoreLocation())
                            .storeInfoOneline(store.getStoreInfoOneline())
                            .storeImg(store.getStoreImg())
                            .votedCount(pastStoreForVote.getVotedCount())
                            .whenForVote(pastStoreForVote.getWhenForVote())
                            .isSelected(pastStoreForVote.isSelected())
                            .build();

                })
                .sorted((s1, s2) -> s2.getVotedCount() - s1.getVotedCount())
                .collect(Collectors.toList());

        StoreResponseEntityDTO storeResponseEntityDTO = StoreResponseEntityDTO.builder()
                .status(200)
                .message("성공")
                .stores(storesList)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponseEntityDTO);
    }
}