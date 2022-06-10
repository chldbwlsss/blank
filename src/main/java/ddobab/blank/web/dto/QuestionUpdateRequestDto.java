package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {

    private String content;

    @Builder
    public QuestionUpdateRequestDto(String content) {
        this.content = content;
    }
}
