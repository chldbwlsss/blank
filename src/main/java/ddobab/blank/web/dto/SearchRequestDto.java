package ddobab.blank.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class SearchRequestDto {

    private String category;
    private String word;
}
