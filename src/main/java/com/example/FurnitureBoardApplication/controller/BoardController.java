package com.example.FurnitureBoardApplication.controller;

import com.example.FurnitureBoardApplication.controller.form.BoardForm;
import com.example.FurnitureBoardApplication.controller.form.CommentForm;
import com.example.FurnitureBoardApplication.entity.Board;
import com.example.FurnitureBoardApplication.entity.Comment;
import com.example.FurnitureBoardApplication.entity.Member;
import com.example.FurnitureBoardApplication.service.BoardService;
import com.example.FurnitureBoardApplication.service.CommentService;
import com.example.FurnitureBoardApplication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;


    /**
     * 게시글 작성 페이지 이동
     */
    @GetMapping("/boards/write/{id}")
    public String boardWriteForm(@PathVariable Long id, Model model) {
        Member member = memberService.findOneId(id);
        BoardForm boardForm = new BoardForm();
        boardForm.setWriter(member.getNickName());
        model.addAttribute("boardForm", boardForm);
        return "boards/boardWriteForm";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/boards/write")
    public String boardWrite(@Valid BoardForm boardForm, BindingResult bindingResult, Model model,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "boards/boardWriteForm";
        }

        HttpSession httpSession = request.getSession();
        Long memberId = (Long) httpSession.getAttribute("memberId");
        Member member = memberService.findOneId(memberId);
        Board board = Board.createBoard(boardForm.getTitle(), boardForm.getWriter(), boardForm.getContent(), 0L, 0.0, member);
        boardService.save_board(board);
        boardService.setCreatedDate(board);
        model.addAttribute("boardWriteSuccess", "게시글을 작성하였습니다.");
        return "redirect:/";
    }

    /**
     * 게시글 보기 페이지 이동 (이동 전에 해당 게시글의 Hidden값이 1이면 메인 페이지로 이동한다.)
     */
    @GetMapping("/boards/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        Board board = boardService.findOneBoard(id);
        List<Comment> commentList = commentService.find_Comment(id);
        if (board.getHidden() == 0) {
            boardService.addViewsBoard(board.getBoardId()); // 조회수 증가
            model.addAttribute("board", board);
            model.addAttribute("commentList", commentList);
            model.addAttribute("CommentForm", new CommentForm());
            return "boards/boardDetail";
        } else {
            return "redirect:/";
        }
    }

    /**
     * 게시글 수정 페이지 이동
     */
    @GetMapping("/boards/update/{id}")
    public String boardUpdate(@PathVariable Long id, BoardForm boardForm, Model model) {
        Board board = boardService.findOneBoard(id);

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setWriter(board.getWriter());

        model.addAttribute("boardUpdate_id", String.valueOf(board.getBoardId()));
        return "boards/boardUpdateForm";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/boards/update/{id}")
    public String boardUpdate(@PathVariable Long id, @Valid BoardForm boardForm) {
        boardService.updateBoard(id, boardForm);
        return "redirect:/";
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/boards/delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.removeBoard(id);
        return "redirect:/";
    }
}
