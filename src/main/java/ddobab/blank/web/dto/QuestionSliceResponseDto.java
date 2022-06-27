package ddobab.blank.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionSliceResponseDto {

    List<QuestionResponseDto> questions;
    private boolean hasNext;

}
