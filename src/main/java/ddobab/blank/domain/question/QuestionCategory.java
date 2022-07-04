package ddobab.blank.domain.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuestionCategory {

    NONE("선택안함", "NONE"), EDUCATION("교육", "EDUCATION"), IT("컴퓨터", "IT"),
    GAME("게임", "GAME"), ART("예술", "ART"), LIVING("생활", "LIVING"),
    HEALTH("건강", "HEALTH"), SOCIETY("사회", "SOCIETY"), ECONOMY("경제", "ECONOMY"),
    TRAVEL("여행", "TRAVEL"), SHOPPING("쇼핑", "SHOPPING"), SPORT("스포츠", "SPORT");

    private final String korValue;
    private final String engValue;

    QuestionCategory(String korValue, String engValue) {
        this.korValue = korValue;
        this.engValue = engValue;
    }
}
