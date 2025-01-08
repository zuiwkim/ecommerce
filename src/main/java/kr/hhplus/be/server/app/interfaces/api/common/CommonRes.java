package kr.hhplus.be.server.app.interfaces.api.common;

import kr.hhplus.be.server.app.interfaces.exception.ExceptionMessage;

public record CommonRes<T>(
        ResultType resultType,
        T data,
        ExceptionMessage exception
) {
    public static <T> CommonRes<T> success(T data){
        return new CommonRes<>(ResultType.SUCCESS, data, new ExceptionMessage());
    }
}
