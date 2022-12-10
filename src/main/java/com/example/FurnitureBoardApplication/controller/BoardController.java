package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.BoardForm;
import com.example.FurnitureBoardApplication.domain.Board;
import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    /**
     * 게시글 작성 페이지 이동
     */
    @GetMapping("/boards/write/{id}")
   public String boardWriteForm(@PathVariable Long id, Model model){
        Member member = memberService.findOneId(id);
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("memberNickName", member.getNickName());
        model.addAttribute("boardWrite", "boardWrite");
        return "boards/boardWriteForm";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/boards/write")
    public String boardWrite(@Valid BoardForm boardForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "boards/boardWriteForm";
        }

        Board board = Board.createBoard(boardForm.getTitle(), boardForm.getWriter(), boardForm.getContent(), 0L, 0.0);
        boardService.save_board(board);
        boardService.setCreatedDate(board);
        model.addAttribute("boardWriteSuccess", "게시글을 작성하였습니다.");
        return "redirect:/";
    }

    /**
     * 게시글 보기 페이지 이동 (이동 전에 해당 게시글의 Hidden값이 1이면 메인 페이지로 이동한다.)
     */
    @GetMapping("/boards/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model){
        Board board = boardService.findOneBoard(id);
        if (board.getHidden() == 0){
            boardService.addViewsBoard(board.getId()); // 조회수 증가
            model.addAttribute("board", board);
            return "boards/boardDetail";
        }else {
            return "redirect:/";
        }
    }

    /**
     * 게시글 수정 페이지 이동
     */
    @GetMapping("/boards/update/{id}")
    public String boardUpdate(@PathVariable Long id, BoardForm boardForm, Model model){
        Board board = boardService.findOneBoard(id);

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setWriter(board.getWriter());

        model.addAttribute("boardUpdate_id", String.valueOf(board.getId()));
        return "boards/boardUpdateForm";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/boards/update/{id}")
    public String boardUpdate(@PathVariable Long id, @Valid BoardForm boardForm){
        System.out.println("boardUpdate: "+id);
        boardService.updateBoard(id, boardForm);
        return "redirect:/";
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/boards/delete/{id}")
    public String boardDelete(@PathVariable Long id){
        boardService.removeBoard(id);
        return "redirect:/";
    }
}
