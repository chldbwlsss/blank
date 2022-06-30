package ddobab.blank.web;

import ddobab.blank.exception.customException.UnauthorizedException;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/answer")
@RestController
public class AnswerApiV1Controller {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<ResponseDto<AnswerResponseDto>> save(@LoginUser SessionUserDto loginUser, @RequestBody AnswerSaveRequestDto requestDto) {
        //bean validation 필요
        if(loginUser!=null){
            AnswerResponseDto data = answerService.save(loginUser.getNo(), requestDto);
            return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.CREATED);
        }else{
            throw new UnauthorizedException("데이터 생성 권한이 없습니다.");
        }
    }

    @GetMapping  //질문번호로 답변리스트 가져옴
    public ResponseEntity<ResponseDto<AnswerSliceResponseDto>> getAnswers(@RequestParam("questionNo") Long questionNo,@RequestParam("page") String page, @RequestParam("size") String size){
       //잘못된 파라미터 요청(bad request), 잘못된 타입 요청
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        AnswerSliceResponseDto data = answerService.findAnswers(pageRequest, questionNo);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<AnswerResponseDto>> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody AnswerUpdateRequestDto requestDto) {
       //bean validation
        if(loginUser!=null) {
            Long writerNo = answerService.getAnswerWriter(no);
            if (writerNo.equals(loginUser.getNo())) {
                AnswerResponseDto data = answerService.update(no, requestDto);
                return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
            } else {
                throw new UnauthorizedException("데이터 변경 권한이 없습니다.");
            }
        }else{
            throw new UnauthorizedException("데이터 변경 권한이 없습니다.");
        }


    }

    @DeleteMapping("/{no}")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {

        if(loginUser!=null){
            Long writerNo = answerService.getAnswerWriter(no);
            if (writerNo.equals(loginUser.getNo())) {
                answerService.delete(no);
                return new ResponseEntity<>(new ResponseDto<>(null, null), HttpStatus.OK);
            } else {
                throw new UnauthorizedException("데이터 삭제 권한이 없습니다.");
            }
        }else{
            throw new UnauthorizedException("데이터 삭제 권한이 없습니다.");
        }

    }
}
