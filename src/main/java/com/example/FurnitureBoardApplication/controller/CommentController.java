package com.example.FurnitureBoardApplication.controller;

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
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;
    private final MemberService memberService;

    /**
     * 게시글 댓글 작성
     */
    @PostMapping("/comments/write")
    public String commentWrite(@Valid CommentForm commentForm, BindingResult bindingResult, HttpServletRequest request,
                               Model model) {
        Long boardId = Long.valueOf(request.getParameter("boardId"));
        Board board = boardService.findOneBoard(boardId);

        model.addAttribute("board", board);
        model.addAttribute("CommentForm", new CommentForm());

        HttpSession httpSession = request.getSession();
        String memberName = (String) httpSession.getAttribute("memberName");
        Long memberId = (Long) httpSession.getAttribute("memberId");
        Member member = memberService.findOneId(memberId);

        if (bindingResult.hasErrors()) {
            List<Comment> commentList = commentService.find_Comment(boardId); // 댓글이 제대로 입력되지 않아서 에러난 경우 기존 양식 그대로 돌아와야하기에 기존 댓글 list를 다시 model에 넣는다.
            model.addAttribute("commentList", commentList);
            return "boards/boardDetail";
        }else {
            Comment comment = Comment.createComment(memberName, commentForm.getContent(), 0.0, member, board);
            commentService.save_Comment(comment);
            commentService.setCreatedDate(comment);
            List<Comment> commentList = commentService.find_Comment(boardId);
            model.addAttribute("commentList", commentList);
            return "boards/boardDetail";
        }
    }

    /**
     * 댓글 삭제
     */
    @GetMapping("/comments/delete/{commentId}/{boardId}")
    public String removeComment(@PathVariable Long commentId, @PathVariable Long boardId, Model model) {
        commentService.delete_Comment(commentId);
        boardRefresh_delete(model, boardId); // 게시글 새로고침 함수
        return "boards/boardDetail";
    }

    /**
     * 댓글 수정
     */
    @PostMapping("/comments/update/{commentId}")
    public String commentUpdate(@Valid CommentForm commentForm, BindingResult bindingResult, Model model,
                                @PathVariable Long commentId, HttpServletRequest request) {
        return boardRefresh_write_update(commentId, commentForm, model, request, bindingResult);
    }

    /**
     * 게시글 새로고침 메소드 (삭제)
     */
    public void boardRefresh_delete(Model model,Long boardId) {
        Board board = boardService.findOneBoard(boardId);
        List<Comment> commentList = commentService.find_Comment(boardId);
        model.addAttribute("board", board);
        model.addAttribute("CommentForm", new CommentForm());
        model.addAttribute("commentList", commentList);
    }

    /**
     * 게시글 새로고침 메소드 (작성, 수정)
     */
    public String boardRefresh_write_update(Long commentId, CommentForm commentForm, Model model,
                                            HttpServletRequest request, BindingResult bindingResult) {
        Long boardId = Long.valueOf(request.getParameter("boardId"));
        Board board = boardService.findOneBoard(boardId);

        model.addAttribute("board", board);
        model.addAttribute("CommentForm", new CommentForm());

        if (bindingResult.hasErrors()) {
            List<Comment> commentList = commentService.find_Comment(boardId); // 댓글이 제대로 입력되지 않아서 에러난 경우 기존 양식 그대로 돌아와야하기에 기존 댓글 list를 다시 model에 넣는다.
            model.addAttribute("commentList", commentList);
            return "boards/boardDetail";
        }else {
            commentService.update_Comment(commentId, commentForm.getContent());
            List<Comment> commentList = commentService.find_Comment(boardId);
            model.addAttribute("commentList", commentList);
            return "boards/boardDetail";
        }
    }
}
