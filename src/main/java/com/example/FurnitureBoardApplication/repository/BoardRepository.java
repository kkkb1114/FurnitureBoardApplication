package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.dto.QMember;
import com.example.FurnitureBoardApplication.entity.Board;
import com.example.FurnitureBoardApplication.dto.QBoard;
import com.mysql.cj.QueryResult;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        JPAQuery<Board> query = new JPAQuery<>(entityManager);
        QBoard qBoard = new QBoard("b");
        return query.from(qBoard)
                .orderBy(qBoard.id.desc())
                .fetch();
    }

    // pk기준 게시글 조회
    public Board findOneBoard(Long id){
        return entityManager.find(Board.class, id);
    }


    /**
     * 검색 기능은 Querydsl강의를 들은 후에 만드는 것이 좋아 보인다.
     */
    // 게시글 전체 기준 검색(작성자, 제목, 내용)
    public List<Board> findTotalBoard(String searchData){
        JPAQuery<Board> query = new JPAQuery<>(entityManager);
        QBoard qBoard = new QBoard("b");
        return query.from(qBoard)
                .where(qBoard.writer.contains(searchData)
                .or(qBoard.title.contains(searchData))
                .or(qBoard.content.contains(searchData)))
                .orderBy(qBoard.id.desc())
                .fetch();
    }

    // 게시글 작성자 기준 검색
    public List<Board> findWriterBoard(String writer){
        JPAQuery<Board> query = new JPAQuery<>(entityManager);
        QBoard qBoard = new QBoard("b");
        return query.from(qBoard)
                .where(qBoard.writer.contains(writer))
                .orderBy(qBoard.id.desc())
                .fetch();
    }

    // 게시글 제목 기준 검색
    public List<Board> findTitleBoard(String title){
        JPAQuery<Board> query = new JPAQuery<>(entityManager);
        QBoard qBoard = new QBoard("b");
        return query.from(qBoard)
                .where(qBoard.title.contains(title))
                .orderBy(qBoard.id.desc())
                .fetch();
    }

    // 게시글 내용 기준 검색
    public List<Board> findContentBoard(String content){
        JPAQuery<Board> query = new JPAQuery<>(entityManager);
        QBoard qBoard = new QBoard("b");
        return query.from(qBoard)
                .where(qBoard.content.contains(content))
                .orderBy(qBoard.id.desc())
                .fetch();
    }

    /** 페이징 **/

}
