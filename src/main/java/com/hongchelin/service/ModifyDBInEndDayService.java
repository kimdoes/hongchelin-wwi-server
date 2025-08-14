package com.hongchelin.service;

import com.hongchelin.Domain.PastStoreForVote;
import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForSelected;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.PastStoreRepositoryInterface;
import com.hongchelin.Repository.StoreForSelectedRepository;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.Repository.StoreRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ModifyDBInEndDayService {
    private final StoreForSelectedRepository storeForSelectedRepository;
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    private final StoreRepositoryInterface storeRepository;
    private final PastStoreRepositoryInterface pastStoreRepository;
    Date date = new Date();

    public ModifyDBInEndDayService(StoreForVoteRepositoryInterface storeForVoteRepository,
                                   StoreRepositoryInterface storeRepository,
                                   StoreForSelectedRepository storeForSelectedRepository,
                                   PastStoreRepositoryInterface pastStoreRepository) {
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeRepository = storeRepository;
        this.storeForSelectedRepository = storeForSelectedRepository;
        this.pastStoreRepository = pastStoreRepository;
    }

    @Transactional
    @Scheduled(cron = "* 30 00 1 * *")
    public /*ResponseEntity<ResponseDTO>*/ void modifyInEnd() {
        List<StoreForVote> stores = storeForVoteRepository.findTop3ByOrderByVotedCountDesc();
        List<StoreForSelected> selectedList = new ArrayList<>();
        List<PastStoreForVote> unSelectedList = new ArrayList<>();
        List<PastStoreForVote> selected = new ArrayList<>();

        for (StoreForVote store : stores) {
            long storeId = store.getStoreId();
            Store individualizedStore = storeRepository.findById(storeId);

            long selectedStoreId = individualizedStore.getId();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            String whenForSelected = formatter.format(date);

            Integer votedCount = store.getVotedCount();

            StoreForSelected storeForSelected = StoreForSelected.builder()
                    .WhenSelected(whenForSelected)
                    .idInDb(selectedStoreId)
                    .votedCount(votedCount)
                    .build();

            selectedList.add(storeForSelected);

            /* 여기서부터는 PAST_FOR_STORE 에 추가할 부분입니다.*/
            PastStoreForVote pastStoreForVote = PastStoreForVote.builder()
                    .isSelected(true)
                    .whenForVote(whenForSelected)
                    .storeId(storeForSelected.getIdInDb())
                    .votedCount(storeForSelected.getVotedCount())
                    .build();

            selected.add(pastStoreForVote);
        }

        for (PastStoreForVote pastStoreForVote : selected) {
            long storeId = pastStoreForVote.getStoreId();

            pastStoreRepository.save(pastStoreForVote);
            storeForVoteRepository.deleteByStoreId(storeId);
        }

        Iterable<StoreForVote> storeForVotes = storeForVoteRepository.findAll();

        for (StoreForVote storeForVote : storeForVotes) {
            Store individualizedStore = storeRepository.findById(storeForVote.getStoreId());

            long selectedStoreId = individualizedStore.getId();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            String whenForSelected = formatter.format(date);

            Integer votedCount = storeForVote.getVotedCount();

            PastStoreForVote pastStoreForVote = PastStoreForVote.builder()
                    .whenForVote(whenForSelected)
                    .storeId(selectedStoreId)
                    .votedCount(votedCount)
                    .isSelected(false)
                    .build();

            unSelectedList.add(pastStoreForVote);
        }

        for (StoreForSelected store : selectedList) {
            storeForSelectedRepository.save(store);
        }

        for (PastStoreForVote pastStoreForVote : unSelectedList) {
            pastStoreRepository.save(pastStoreForVote);
            storeForVoteRepository.deleteByStoreId(pastStoreForVote.getStoreId());
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(200)
                .message("성공")
                .build();

        //return ResponseEntity.ok(responseDTO);
    }
}
