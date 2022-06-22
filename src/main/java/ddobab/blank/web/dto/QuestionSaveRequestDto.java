package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private Long userNo;
    private String content;
    private String categoryValue;
//    private List<MultipartFile> questionImgFiles;

    @Builder
    public QuestionSaveRequestDto(Long userNo, String content, String categoryValue) {
        this.userNo = userNo;
        this.content = content;
        this.categoryValue = categoryValue;
    }

    public void setUserNo(Long userNo){
        this.userNo = userNo;
    }

}
