package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AnswerUpdateRequestDto {

    private String content;
//    private List<String> answerImgUrls;

    @Builder
    public AnswerUpdateRequestDto(String content) {
        this.content = content;
    }
}
