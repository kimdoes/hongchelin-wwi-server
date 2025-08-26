/**
 * issuingcertificationNumber - 홍대 로그인 시 인증번호 발급 -> 로그인 메일이 맞지 않으면 거부 -> 입력창으로 리다이렉트 시킴
 * matchingcertificationNumber - 발급 받은 비밀번호 대조
 */
package com.hongchelin.service.login.HongikUniv;

public interface hongikUnivLoginInterface {
    String issuingcertificationNumber();
    String matchingcertificationNumber();
}