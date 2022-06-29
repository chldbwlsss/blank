package ddobab.blank.web.dto;

import ddobab.blank.exception.dto.ErrorDto;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    T data;
    ErrorDto error;
}
