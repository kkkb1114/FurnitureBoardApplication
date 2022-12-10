package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.domain.Board;
import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
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
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
        if (memberId != null){
            model.addAttribute("memberId", "memberId");
        }
        List<Board> boardList = boardService.findAllBoard();
        model.addAttribute("boardList", boardList);
        return "home/home";
    }
}
