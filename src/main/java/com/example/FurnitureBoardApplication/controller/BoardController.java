package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.BoardForm;
import com.example.FurnitureBoardApplication.domain.Board;
import com.example.FurnitureBoardApplication.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards/save")
    public String boardWriteForm(Model model){
        model.addAttribute("BoardForm", new BoardForm());
        return "boards/boardWriteForm";
    }

    @PostMapping("/boards/save")
    public String boardWrite(@Valid BoardForm boardForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "boards/boardWriteForm";
        }

        Board board = Board.createBoard(boardForm.getTitle(), boardForm.getWriter(), boardForm.getContent(), 0.0);
        Long id = boardService.save_board(board);
        model.addAttribute("boardWriteSuccess", "게시글을 작성하였습니다.");
        return ;
    }
}
