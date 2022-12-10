package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.LoginForm;
import com.example.FurnitureBoardApplication.controller.form.MemberForm;
import com.example.FurnitureBoardApplication.controller.form.PasswordUpdateForm;
import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String memberJoin(@Valid MemberForm memberForm, BindingResult bindingResult, Model model) {
        // 데이터중 하나라도 입력되지 않으면 경고
        if (bindingResult.hasErrors()) {
            return "members/memberJoinForm";
        }

        // 정상 회원 가입
        Member member = Member.createMember(memberForm.getEmail(), memberForm.getPassword(), memberForm.getNickName(),
                memberForm.getAddress(), memberForm.getDetailedAddress(), 0.0);
        memberService.memberJoin(member);
        model.addAttribute("memberJoinSuccess", "환영합니다.");
        model.addAttribute("loginForm", new LoginForm());
        //return "redirect:/";
        return "members/loginForm";
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
    public String memberLogin(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request,
                              @RequestParam(value = "autoLogin", required = false) boolean autoLogin) {
        // 아이디, 비밀번호 둘중 하나라도 입력되지 않으면 경고
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        // 아이디 or 비밀번호가 일치한
        if (memberService.memberLogin(loginForm.getEmail(), loginForm.getPassword()) != null) {
            Member member = memberService.memberLogin(loginForm.getEmail(), loginForm.getPassword());
            HttpSession httpSession = request.getSession();
            if (autoLogin) {
                // AutoLogin 세션 저장(이건 세션으로 하면 일반 로그인과 다를바가 없어 !!!!쿠키로 만들어야 할 듯 하다!!!!)
                httpSession.setAttribute("AutoLogin", "AutoLogin");
            }

            httpSession.setAttribute("memberId", member.getId()); // memberId 세션 저장
            return "redirect:/";
        } else {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
    }

    /**
     * 내 정보 페이지 이동
     */
    @GetMapping("/members/myInfo/{id}")
    public String memberFindAll(@PathVariable Long id, Model model) {
        Member member = memberService.findOneId(id);
        model.addAttribute("memberMyInfo", member);
        return "members/memberMyInfo";
    }

    /**
     * 비밀번호 변경 페이지 이동
     */
    @GetMapping("/members/passwordUpdate/{id}")
    public String passwordUpdateForm(@PathVariable Long id, PasswordUpdateForm passwordUpdateForm, Model model) {
        model.addAttribute("memberId", id);
        model.addAttribute("passwordUpdateForm", passwordUpdateForm);
        return "members/passwordUpdateForm";
    }

    /**
     * 비밀번호 변경 페이지 이동
     */
    @PostMapping("/members/passwordUpdate/{id}")
    public String passwordUpdate(@PathVariable Long id, @Valid PasswordUpdateForm passwordUpdateForm, BindingResult bindingResult,
                                 HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/passwordUpdateForm";
        }
        if (!passwordUpdateForm.getUpdatePassword().equals(passwordUpdateForm.getUpdatePasswordConfirm())) {
            bindingResult.reject("입력하신 변경 비밀번호가 맞지 않습니다.");
            return "members/passwordUpdateForm";
        }

        Member member = memberService.findOneId(id);
        if (member.getPassword().equals(passwordUpdateForm.getUpdatePasswordConfirm())) {
            HttpSession httpSession = request.getSession();
            httpSession.removeAttribute("memberId");
            httpSession.removeAttribute("AutoLogin");

            member.passwordUpdate(passwordUpdateForm.getUpdatePasswordConfirm());
            return "members/loginForm";
        } else {
            bindingResult.reject("입력하신 현재 비밀번호가 일치하지 않습니다.");
            return "members/passwordUpdateForm";
        }
    }

    /**
     * 회원 탈퇴
     */
    @GetMapping("/members/delete/{id}")
    public String memberDelete(@PathVariable Long id, HttpServletRequest request){
        memberService.memberDelete(id);
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("memberId");
        httpSession.removeAttribute("AutoLogin");
        return "redirect:/";
    }
}
