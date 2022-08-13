package ddobab.blank.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String errorCode;
    private String message;
}
