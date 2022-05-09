package com.hanul.alcoholic;

/**
 *  사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String idToken; //firebase Uid (고유 토큰 번호)
    private String emailID; // 이메일 아이디
    private String password; // 비밀번호

    public UserAccount() { }

    public String getIdToken() { return idToken; }

    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getEmailID() { return emailID; }

    public void setEmailID(String emailID) { this.emailID = emailID; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
