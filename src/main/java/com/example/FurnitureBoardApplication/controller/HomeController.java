package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.dto.Board;
import com.example.FurnitureBoardApplication.dto.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
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
     * 1. 메인 페이지 이동
     * 2. hidden값이 0인 게시글만 보여준다.
     */
    @GetMapping("/")
    public String home(@CookieValue(name = "AutoLogin", required = false) String memberId, Model model,
                       HttpServletRequest request){
        if (memberId != null){
            Member member = memberService.findOneId(Long.valueOf(memberId));
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("memberId", member.getId()); // memberId 세션 저장
            httpSession.setAttribute("memberName", member.getNickName()); // memberId 세션 저장
            return "home/home";
        }
        List<Board> boardList = boardService.findAllBoard();
        model.addAttribute("boardList", boardList);
        return "home/home";
    }
}
