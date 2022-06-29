package ddobab.blank.exception.advice;

import ddobab.blank.exception.dto.ErrorDto;
import ddobab.blank.web.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExAdvice {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<?>> badRequest(IllegalArgumentException e){
        ErrorDto error = new ErrorDto("REQUEST", e.getMessage());

        return new ResponseEntity<>(new ResponseDto<>(null, error), HttpStatus.BAD_REQUEST);
    }
}
