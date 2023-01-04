package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.LoginForm;
import com.example.FurnitureBoardApplication.controller.form.MemberForm;
import com.example.FurnitureBoardApplication.controller.form.PasswordUpdateForm;
import com.example.FurnitureBoardApplication.entity.Mail;
import com.example.FurnitureBoardApplication.entity.Member;
import com.example.FurnitureBoardApplication.service.MailService;
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
    private final MailService mailService;

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
    public String memberLogin(@Valid LoginForm loginForm, BindingResult bindingResult,
                              @RequestParam(value = "autoLogin", required = false) boolean autoLogin,
                              HttpServletResponse response, HttpServletRequest request) {
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
                //httpSession.setAttribute("AutoLogin", "AutoLogin");
                Cookie cookie = new Cookie("AutoLogin", String.valueOf(member.getMemberId()));
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60);
                cookie.setSecure(true);
                response.addCookie(cookie);
            }

            httpSession.setAttribute("memberId", member.getMemberId()); // memberId 세션 저장
            httpSession.setAttribute("memberName", member.getNickName()); // memberId 세션 저장
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
     * 비밀번호 변경
     */
    @PostMapping("/members/passwordUpdate/{id}")
    public String passwordUpdate(@PathVariable Long id, @Valid PasswordUpdateForm passwordUpdateForm, BindingResult bindingResult,
                                 HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/passwordUpdateForm";
        }

        String nowPassword = passwordUpdateForm.getNowPassword();
        String UpdatePassword = passwordUpdateForm.getUpdatePassword();
        String UpdatePasswordConfirm = passwordUpdateForm.getUpdatePasswordConfirm();

        Member member = memberService.findOneId(id);

        if (member.getPassword().equals(nowPassword)) {
            if (!UpdatePassword.equals(UpdatePasswordConfirm)) {
                bindingResult.reject("입력하신 현재 비밀번호가 일치하지 않습니다.");
                return "members/passwordUpdateForm";
            } else {
                HttpSession httpSession = request.getSession();
                httpSession.removeAttribute("memberId");
                httpSession.removeAttribute("AutoLogin");

                memberService.passwordUpdate(member, UpdatePasswordConfirm);
                return "members/loginForm";
            }
        } else {
            bindingResult.reject("입력하신 현재 비밀번호가 일치하지 않습니다.");
            return "members/passwordUpdateForm";
        }
    }

    /**
     * 로그아웃
     */
    @GetMapping("/members/logout")
    public String memberDelete(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("memberId");
        Cookie cookie = new Cookie("AutoLogin", null);
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "redirect:/";
    }

    /**
     * 회원 탈퇴
     */
    @GetMapping("/members/delete/{id}")
    public String memberDelete(@PathVariable Long id, HttpServletRequest request) {
        memberService.memberDelete(id);
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("memberId");
        httpSession.removeAttribute("AutoLogin");
        return "redirect:/";
    }

    /**
     * 비밀번호 찾기 페이지 이동
     */
    @GetMapping("/members/findPassword")
    public String memberFindPassword() {
        return "members/memberFindPassword";
    }

    /**
     * 비밀번호 찾기 -임시 비밀번호로 변경-
     */
    @PostMapping("/members/sendPassword")
    public String sendPassword(@RequestParam("userEmail") String userEmail, Model model) {
        //임시 비밀번호 생성
        Member member = memberService.memberFindOneEmail(userEmail);
        String tmpPassword = memberService.getTmpPassword();
        // 임시 비밀번호 저장
        memberService.passwordUpdate(member, tmpPassword);
        
        // 메일 생성 및 전송
        Mail mail = mailService.createMail(tmpPassword, userEmail);
        mailService.sendMail(mail);
        
        System.out.println("임시 비밀번호 메일 전송 완료");

        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    /**
     * 이메일 존재하는지 확인
     **/
    @GetMapping("/members/checkEmail")
    public boolean checkEmail(@RequestParam("userEmail") String userEmail) {
        System.out.println("이메일존재하는지확인");
        return memberService.checkEmail(userEmail);
    }
}
