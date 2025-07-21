/**
 * 매장정보를 불러오는 DTO입니다.
 *
 * @author dx2d2y
 *
 * @param identifier        매장 식별자
 * @param location          매장 위치. 지도에 표시될 정보로 위도-경도 순으로 리스트에 들어갑니다.
 * @param storeName         매장 이름
 * @param storeInfoOneLine  매장 한줄평
 * @param storeMenu         매장 메뉴
 * @param votedAmount       투표 받은 횟수
 */
package com.hongchelin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
public class StoreDTO {
    String identifier;
    ArrayList<String> location;
    String storeName;
    String storeInfoOneLine;
    ArrayList<String> storeMenu;
    Integer votedAmount;
}
