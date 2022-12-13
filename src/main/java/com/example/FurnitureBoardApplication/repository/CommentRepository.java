package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.dto.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager entityManager;

    // 댓글 등록
    public Long save_Comment(Comment comment){
        if (comment.getId() != null){
            entityManager.merge(comment);
        }else {
            entityManager.persist(comment);
        }
        return comment.getId();
    }

    public Comment findOneComment(Long id){
        return entityManager.find(Comment.class, id);
    }

    // todo 댓글 조회 (게시글에 해당하는 댓글만) 일단 전부 찾기로 만들고 Querydsl을 빨리 습득하자
    public List<Comment> findComment(Long boardId){
        List<Comment> commentList = entityManager.createQuery("select c from Comment c where c.hidden = 0 and c.board.id = :boardId", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
        return commentList;
    }
}
