package ddobab.blank.exception.advice;

import ddobab.blank.exception.dto.ErrorDto;
import ddobab.blank.web.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<?>> allOtherException(RuntimeException e) {
        ErrorDto error = new ErrorDto("SERVER", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<?>> accessDeny(AccessDeniedException e) {
        ErrorDto error = new ErrorDto("CLIENT", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<?>> badRequest(IllegalArgumentException e) {
        ErrorDto error = new ErrorDto("CLIENT", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDto<?>> notFound(NoSuchElementException e) {
        ErrorDto error = new ErrorDto("CLIENT", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDto<?>> server(IllegalStateException e) {
        ErrorDto error = new ErrorDto("SERVER", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> bindException(MethodArgumentNotValidException e) {

        ErrorDto error = new ErrorDto("CLIENT", e.getFieldError().getDefaultMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> bindException(HttpRequestMethodNotSupportedException e) {

        ErrorDto error = new ErrorDto("CLIENT", e.getMessage());
        log.error("EX : {}", error);
        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
