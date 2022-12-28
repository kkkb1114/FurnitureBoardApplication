package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.dto.BoardDto;
import com.example.FurnitureBoardApplication.entity.Board;
import com.example.FurnitureBoardApplication.entity.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;

    /**
     * 메인 페이지
     */
    /*@GetMapping("/")
    public String home(Model model) {
        return "home/home";
    }*/

    /**
     * <메인 페이지 이동>
     * 1. hidden값이 0인 게시글만 보여준다.
     * 2. 쿠키가 존재한다면 로그인 세션을 등록후 홈 화면으로 이동. (그럼 로그인 됨)
     * 3. 메인 페이지가 게시판이기에 페이징할 데이터를 같이 보낸다.
     */
    @GetMapping("/")
    public String home(@CookieValue(name = "AutoLogin", required = false) String memberId, Model model,
                       HttpServletRequest request, String searchVal, Pageable pageable){
        /*Member member1 = memberService.findOneId(8L);
        for (int i=100; i<200; i++){
            Board board = Board.createBoard("제목"+i, member1.getNickName(), "내용"+i, 0L, 0.0, member1);
            boardService.save_board(board);
        }*/
        if (memberId != null){
            Member member = memberService.findOneId(Long.valueOf(memberId));
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("memberId", member.getId()); // memberId 세션 저장
            httpSession.setAttribute("memberName", member.getNickName()); // memberId 세션 저장
            return "home/home";
        }
        Page<BoardDto> results = boardService.getBoardList(pageable);
        model.addAttribute("boardList", results);
        model.addAttribute("maxPage", results.getSize());
        pageModelPut(results, model);



        //List<BoardDto> boardList = boardService.findAllBoard();
        //model.addAttribute("boardList", boardList);
        return "home/home";
    }

    private void pageModelPut(Page<BoardDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());
    }
}
