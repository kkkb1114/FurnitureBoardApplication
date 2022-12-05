package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    /**
     * 1. 메인 페이지에서 로그인 쿠키가 없으면 전부 로그인 페이지로 이동한다.
     * 2. 로그인 쿠키가 있으면 메인페이지에 머문다.(홈이 없어서 임시로 이렇게 만듬)
     */
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
        if (memberId == null){
            return "members/loginForm";
        }

        Member member = memberService.findOneId(memberId);
        if (member == null){
            return "members/loginForm";
        }

        model.addAttribute("memberList", member);
        return "members/memberList";
    }
}
