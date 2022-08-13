package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {

    @NotBlank(message = "질문 내용은 빈 칸일 수 없습니다.")
    @Length(max = 500, message = "질문은 500자 이하로 작성 가능합니다.")
    private String content;

    @NotBlank(message = "카테고리를 선택해야 합니다.")
    private String categoryValue;
//    private List<String> questionImgUrls;

    @Builder
    public QuestionRequestDto(String content, String categoryValue) {
        this.content = content;
        this.categoryValue = categoryValue;
    }
}
