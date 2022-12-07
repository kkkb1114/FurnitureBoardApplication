package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.controller.form.BoardForm;
import com.example.FurnitureBoardApplication.domain.Board;
import com.example.FurnitureBoardApplication.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시글 저장
    public Long save_board(Board board) {
        boardRepository.save_board(board);
        return board.getId();
    }

    // 게시글 전부 조회
    @Transactional(readOnly = true)
    public List<Board> findAllBoard(){
        return boardRepository.findAllBoard();
    }

    // pk기준 게시글 조회
    @Transactional(readOnly = true)
    public Board findOneBoard(Long id){
        return boardRepository.findOneBoard(id);
    }

    /**
     * 검색 기능은 Querydsl강의를 들은 후에 만드는 것이 좋아 보인다.
     */
    /*// 게시글 제목 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findTitleBoard(String title){
        return boardRepository.findTitleBoard(title);
    }

    // 게시글 작성자 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findWriterBoard(String writer){
        return boardRepository.findWriterBoard(writer);
    }

    // 게시글 내용 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findContentBoard(String content){
        return boardRepository.findContentBoard(content);
    }*/

    // 게시글 수정
    public void updateBoard(Long id, BoardForm boardForm){
        Board board = boardRepository.findOneBoard(id);
        board.updateBoard(boardForm.getTitle(), boardForm.getWriter(), boardForm.getContent());
    }

    // 게시글 삭제 (hidden = 1 처리)
    public void removeBoard(Long id){
        Board board = boardRepository.findOneBoard(id);
        board.removeBoard();
    }
}
