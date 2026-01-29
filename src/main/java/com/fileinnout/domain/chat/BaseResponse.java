package com.fileinnout.domain.chat;

public class BaseResponse<T> {
    private Boolean status;
    private Integer code;
    private String message;
    private T result;

    public BaseResponse(Boolean status, Integer code, String message, T result) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> BaseResponse success(T result) {
        return new BaseResponse(true, 1000, "성공", result);
    }

    public static <T> BaseResponse fail(T result) {
        return new BaseResponse(false, 4000, "실패", result);
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}