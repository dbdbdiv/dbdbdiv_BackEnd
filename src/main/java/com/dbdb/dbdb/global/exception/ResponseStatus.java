package com.dbdb.dbdb.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatus {
    SUCCESS(true, 200, "요청이 성공했습니다"),

    // Success
    EMAIL_DUPLICATE(true, 2000, "이메일이 중복됩니다"),
    EMAIL_NOT_DUPLICATE(true, 2001, "이메일이 중복되지 않습니다"),
    SUCCESS_LOGIN(true, 2002, "로그인을 성공하였습니다."),
    SUCCESS_LOGOUT(true, 2003, "로그아웃을 성공하였습니다."),
    SUCCESS_SEND_AUTHCODE(true, 2004, "이메일 인증번호 전송을 성공하였습니다"),
    SUCCESS_CORRECT_AUTHCODE(true, 2005, "인증번호가 일치합니다."),
    SUCCESS_NOT_CORRECT_AUTHCODE(true, 2006, "인증번호가 일치하지 않습니다."),
    SUCCESS_CHANGE_PASSWORD(true, 2007, "비밀번호 변경을 성공하였습니다."),
    SUCCESS_WITHDRAWAL(true, 2008, "회원탈퇴를 성공하였습니다"),





    RESULT_NOT_EXIST(true, 2500, "해당하는 데이터가 존재하지 않습니다"),
    BOARD_NOT_EXIST(true, 2501, "해당하는 게시글이 존재하지 않습니다"),
    COMMENT_NOT_EXIST(true, 2502, "해당하는 댓글이 존재하지 않습니다"),

    // Exception
    // 5000 - Request Error
    REQUEST_ERROR(false, 5000, "잘못된 요청입니다."),
    ERROR_LOGIN(false, 5001, "로그인을 실패하였습니다. 이메일 혹은 비밀번호를 다시 확인해주세요."),
    ERROR_TIMEOVER_AUTHCODE(false, 5002, "5분이 지나 해당 인증 번호는 만료되었습니다."),
    INVALID_REQUEST(false, 5100, "입력값을 확인해주세요"),
    UPLOAD_ERROR(false, 5200, "파일 업로드에 실패했습니다."),
    FILE_READ_ERROR(false, 5201, "파일을 조회할 때 오류가 발생했습니다."),
    INVALID_AUTHORITY_NOTICE(false, 5202, "관리자만 공지를 작성할 수 있습니다"),
    INVALID_AUTHORITY_MODIFY(false, 5203, "게시글은 작성자만 수정할 수 있습니다"),
    INVALID_AUTHORITY_DELETE(false, 5204, "게시글은 작성자만 삭제할 수 있습니다"),



    // 6000 - Response Error
    RESPONSE_ERROR(false, 6000, "값을 불러오는데 실패했습니다"),


    // 7000 - Server Connection Error
    SERVER_ERROR(false, 7000, "서버 연결에 실패했습니다."),
    DATABASE_ERROR(false, 7100, "데이터베이스 오류가 발생했습니다."),
    ;

    private final boolean isSuccess;
    private final int code;
    private final String message;
}
