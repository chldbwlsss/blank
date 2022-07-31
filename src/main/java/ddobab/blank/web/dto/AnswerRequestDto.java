package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AnswerRequestDto {

    @NotNull(message = "질문 번호는 필수입니다.")
    private Long questionNo;

    @NotBlank(message = "답변 내용은 빈 칸일 수 없습니다.")
    @Length(max = 500, message = "답변은 500자 이하로 작성 가능합니다.")
    private String content;
//    private List<MultipartFile> answerImgFiles;

    @Builder
    public AnswerRequestDto(String content, Long questionNo) {
        this.content = content;
        this.questionNo = questionNo;
    }
}
