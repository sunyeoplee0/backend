package com.fileinnout.global;

public class BaseResponse <T>{
    private Boolean success;
    private Integer code;
    private String message;
    private T result;

    public BaseResponse(Boolean success, Integer code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static<T> BaseResponse success(T result) {
        return new BaseResponse(true, 1000, "성공", result);
    }

    public static<T> BaseResponse fail(T result) {
        return new BaseResponse(false, 4000, "실패", result);
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
