package kr.hhplus.be.server.app.interfaces.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ExceptionMessage {
    private String code;
    private String message;
    private Object data;

    public ExceptionMessage(Exception exception, HttpStatus status){
        this.code = status.toString();
        this.message = exception.getMessage();
        this.data = null;
    }
}