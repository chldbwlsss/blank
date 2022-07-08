package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private String content;
    private String categoryValue;
//    private List<String> questionImgUrls;

    @Builder
    public QuestionRequestDto(String content, String categoryValue) {
        this.content = content;
        this.categoryValue = categoryValue;
    }
}
