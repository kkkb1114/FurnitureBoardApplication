package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.entity.Comment;
import com.example.FurnitureBoardApplication.entity.QComment;
import com.querydsl.jpa.impl.JPAQuery;
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

    public List<Comment> findComment(Long boardId){
        JPAQuery<Comment> query = new JPAQuery<>(entityManager);
        QComment qComment = new QComment("c");
        return query.from(qComment)
                .where(qComment.board.boardId.eq(boardId))
                .orderBy(qComment.id.desc())
                .fetch();
    }
}
