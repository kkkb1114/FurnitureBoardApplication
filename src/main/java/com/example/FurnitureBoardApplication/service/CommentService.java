package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.dto.Board;
import com.example.FurnitureBoardApplication.dto.Comment;
import com.example.FurnitureBoardApplication.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 등록
    public Long save_Comment(Comment comment){
        return commentRepository.save_Comment(comment);
    }
    // 댓글 삭제
    public void delete_Comment(Long id){
        Comment comment = commentRepository.findOneComment(id);
        comment.removeComment();
    }
    // 댓글 수정
    public void update_Comment(Long id, String content){
        Comment comment = commentRepository.findOneComment(id);
        comment.updateComment(content);
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<Comment> find_Comment(Long boardId){
        return commentRepository.findComment(boardId);
    }

    //==댓글 생성 시간 설정==//
    public void setCreatedDate(Comment comment){
        comment.setCreatedDate(comment.getCreatedDateTime());
    }
}
