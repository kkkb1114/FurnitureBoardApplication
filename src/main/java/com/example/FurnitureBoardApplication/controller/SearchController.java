package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final BoardService boardService;

    /**
     * 제목 기준 게시글 검색
     */
    @GetMapping("/search/Board")
    public String searchBoardTitle(HttpServletRequest request, Model model){
        String searchTitle = request.getParameter("searchTitle");
        String searchData = request.getParameter("searchData");

        model.addAttribute("searchTitle", searchTitle);
        model.addAttribute("searchData", searchData);

        if (searchTitle.equals("전체")){
            model.addAttribute("boardList", boardService.findTotalBoard(searchData));
        }else if (searchTitle.equals("작성자")){
            model.addAttribute("boardList", boardService.findWriterBoard(searchData));
        }else if (searchTitle.equals("제목")){
            model.addAttribute("boardList", boardService.findTitleBoard(searchData));
        }else if (searchTitle.equals("내용")){
            model.addAttribute("boardList", boardService.findContentBoard(searchData));
        }
        return "home/home";
    }
}
