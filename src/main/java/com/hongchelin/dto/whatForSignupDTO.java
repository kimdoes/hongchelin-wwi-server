package com.hongchelin.dto;

public class whatForSignupDTO {
    private static ThreadLocal<Boolean> isForLogin = ThreadLocal.withInitial(() -> false);

    public static ThreadLocal<Boolean> isForLogin() {
        return isForLogin; //로그인일 경우에는 true, 회원 DB 연동을 위해서는 false 로 설정됩니다.
    }

    public static void setForLogin(Boolean value) {
        isForLogin.set(value);
    }
}