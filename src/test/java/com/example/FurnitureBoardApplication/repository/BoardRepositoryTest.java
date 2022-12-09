package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void save_board() {
        //given
        Board board1 = Board.createBoard("제목1", "작성자1", "내용1", 0L, 0.0);

        //when
        Long id = boardRepository.save_board(board1);
        Board board2 = boardRepository.findOneBoard(id);

        //then
        Assertions.assertThat(board2.getTitle()).isEqualTo(board1.getTitle());
    }

    @Test
    void findAllBoard() {
        //given
        Board board1 = Board.createBoard("제목1", "작성자1", "내용1", 0L, 0.0);
        Board board2 = Board.createBoard("제목2", "작성자2", "내용2", 0L, 0.0);

        //when
        Long id = boardRepository.save_board(board1);
        Long id2 = boardRepository.save_board(board2);
        List<Board> boardList = boardRepository.findAllBoard();


        //then
        Assertions.assertThat(boardList.size()).isEqualTo(2);
    }

    @Test
    void findTitleBoard() {
        //given
        Board board1 = Board.createBoard("제목1", "작성자1", "내용1", 0L, 0.0);
        Board board2 = Board.createBoard("제목2", "작성자2", "내용2", 0L, 0.0);

        //when
        Long id = boardRepository.save_board(board1);
        Long id2 = boardRepository.save_board(board2);
        /*List<Board> boardList = boardRepository.findTitleBoard("제목");
        for (Board board : boardList){
            System.out.println("asdsadd:" + board.getTitle());
        }*/
        
        //then
        //Assertions.assertThat(boardList.size()).isEqualTo(1);
    }

    @Test
    void findWriterBoard() {
        //given

        //when

        //then
    }

    @Test
    void findContentBoard() {
        //given

        //when

        //then
    }
}