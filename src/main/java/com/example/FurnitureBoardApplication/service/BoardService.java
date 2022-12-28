package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.controller.form.BoardForm;
import com.example.FurnitureBoardApplication.dto.BoardDto;
import com.example.FurnitureBoardApplication.entity.Board;
import com.example.FurnitureBoardApplication.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        //return boardRepository.findAllBoardPaging(pageable);
        return boardRepository.findAllBoard();
    }

    // pk기준 게시글 조회
    @Transactional(readOnly = true)
    public Board findOneBoard(Long id){
        return boardRepository.findOneBoard(id);
    }

    //==생성 시간 설정==//
    // Board가 DB에 저장된 후에 생성되는 LocalDateTime을 이용하여 만들기에 저장 후에 작성했다.
    // jpa는 저장을 하지 않아도 중간에 데이터가 바뀌면 이를 감지하여 update하는 sql 문을 작성하기 때문에 @Transactional 메서드에 Entity 클래스의 데이터만 수정했다.
    public void setCreatedDate(Board board){
        board.setCreatedDate(board.getCreatedDateTime());
    }

    // 게시글 전체 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findTotalBoard(String searchData){
        return boardRepository.findTotalBoard(searchData);
    }

    // 게시글 작성자 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findWriterBoard(String writer){
        return boardRepository.findWriterBoard(writer);
    }

    // 게시글 제목 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findTitleBoard(String title){
        return boardRepository.findTitleBoard(title);
    }

    // 게시글 내용 기준 검색
    @Transactional(readOnly = true)
    public List<Board> findContentBoard(String content){
        return boardRepository.findContentBoard(content);
    }

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

    //==게시글 조회수 증가==//
    public void addViewsBoard(Long id){
        Board board = boardRepository.findOneBoard(id);
        board.addViewsBoard();
    }


    /** 페이징 **/
    public Page<BoardDto> getBoardList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10);
        return boardRepository.selectBoardList("", pageable);
    }
}
