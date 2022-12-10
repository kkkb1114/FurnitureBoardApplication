package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager entityManager;

    // 게시글 등록
    public Long save_board(Board board){
        if (board.getId() == null){
            entityManager.persist(board);
        }else {
            entityManager.merge(board);
        }
        return board.getId();
    }

    // 게시글 전부 조회
    public List<Board> findAllBoard(){
        return entityManager.createQuery("select b from Board b where b.hidden = 0", Board.class)
                .getResultList();
    }

    // pk기준 게시글 조회
    public Board findOneBoard(Long id){
        return entityManager.find(Board.class, id);
    }


    /**
     * 검색 기능은 Querydsl강의를 들은 후에 만드는 것이 좋아 보인다.
     */
    /*// 게시글 제목 기준 검색
    public List<Board> findTitleBoard(String title){
        return entityManager.createQuery("select b from Board b where b.title like :title", Board.class)
                .setParameter("title", title)
                .getResultList();
    }

    // 게시글 작성자 기준 검색
    public List<Board> findWriterBoard(String writer){
        return entityManager.createQuery("select b from Board b where b.writer = :writer", Board.class)
                .setParameter("writer", writer)
                .getResultList();
    }

    // 게시글 내용 기준 검색
    public List<Board> findContentBoard(String content){
        return entityManager.createQuery("select b from Board b where b.content like :content", Board.class)
                .setParameter("content", content)
                .getResultList();
    }*/
}
