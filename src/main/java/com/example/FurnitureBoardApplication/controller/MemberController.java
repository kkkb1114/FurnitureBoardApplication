package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.LoginForm;
import com.example.FurnitureBoardApplication.controller.form.MemberForm;
import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입 페이지 이동
     */
    @GetMapping("/members/Join")
    public String memberJoinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/memberJoinForm";
    }

    /**
     * 회원 가입 기능
     */
    @PostMapping("/members/Join")
    public String memberJoin(@Valid MemberForm memberForm, BindingResult bindingResult) {
        // 데이터중 하나라도 입력되지 않으면 경고
        if (bindingResult.hasErrors()) {
            return "members/memberJoinForm";
        }

        // 정상 회원 가입
        Member member = Member.createMember(memberForm.getEmail(), memberForm.getPassword(), memberForm.getNickName(),
                memberForm.getAddress(), memberForm.getDetailedAddress(), 0L);
        memberService.memberJoin(member);
        //return "redirect:/";
        return "members/memberList";
    }

    /**
     * 회원 조회
     */
    @GetMapping("/members/List")
    public String memberFindAll(Model model) {
        List<Member> memberList = memberService.memberFindAll();
        model.addAttribute("memberList", memberList);
        return "members/memberList";
    }

    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/members/login")
    public String memberLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    /**
     * 로그인
     */
    @PostMapping("/members/login")
    public String memberLogin(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        // 아이디, 비밀번호 둘중 하나라도 입력되지 않으면 경고
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        // 아이디 or 비밀번호가 일치한
        if (memberService.memberLogin(loginForm.getEmail(), loginForm.getPassword()) != null) {
            Member member = memberService.memberLogin(loginForm.getEmail(), loginForm.getPassword());
            Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
            httpServletResponse.addCookie(cookie);
            //return "redirect:/";
            return "members/memberList";
        } else {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
    }

}
