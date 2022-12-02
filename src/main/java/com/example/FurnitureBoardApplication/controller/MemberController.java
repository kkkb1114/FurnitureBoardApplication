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
        if (bindingResult.hasErrors()) {
            return "members/memberJoinForm";
        } else {
            Member member = Member.createMember(memberForm.getEmail(), memberForm.getPassword(), memberForm.getNickName(),
                    memberForm.getAddress(), memberForm.getDetailedAddress(), 0L);
            memberService.memberJoin(member);
            return "redirect:/";
        }
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
    public String memberLoginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    /**
     * 로그인
     */
    @PostMapping("/members/login")
    public String memberLogin(@ModelAttribute LoginForm loginForm, BindingResult bindingResult){
        if (bindingResult.hasErrors() || memberService.memberLogin(loginForm.getEmail()) == null){
            return "members/loginForm";
        }else {
            //memberService.memberLogin(loginForm.getEmail());
            return "redirect:/";
        }
    }
}
