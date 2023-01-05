package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.dto.BoardDto;
import com.example.FurnitureBoardApplication.entity.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final MemberService memberService;
    private final BoardService boardService;

    /**
     * 제목 기준 게시글 검색
     */
    @GetMapping("/search/Board")
    public String searchBoardTitle(@CookieValue(name = "AutoLogin", required = false) String memberId,
                                   HttpServletRequest request, Model model, Pageable pageable){

        if (memberId != null) {
            Member member = memberService.findOneId(Long.valueOf(memberId));
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("memberId", member.getMemberId()); // memberId 세션 저장
            httpSession.setAttribute("memberName", member.getNickName()); // memberId 세션 저장
        }

        String searchTitle = request.getParameter("searchTitle");
        String searchData = request.getParameter("searchData");

        model.addAttribute("searchTitle", searchTitle);
        model.addAttribute("searchData", searchData);

        Page<BoardDto> results = boardService.getBoardDtoList(searchTitle, searchData, pageable);
        model.addAttribute("boardList", results);
        model.addAttribute("maxPage", results.getSize());
        pageModelPut(results, model);

        return "home/home";
    }

    private void pageModelPut(Page<BoardDto> results, Model model) {
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }
}
