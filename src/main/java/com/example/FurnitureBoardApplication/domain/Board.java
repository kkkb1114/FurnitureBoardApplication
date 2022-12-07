package com.example.FurnitureBoardApplication.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id; // pk
    private String title; // 제목
    private String writer; // 작성자
    private String content; // 내용
    private Double hidden;

    protected Board() {
    }

    //==생성 메서드==//
    public static Board createBoard(String title, String writer, String content, Double hidden) {
        Board board = new Board();
        board.title = title;
        board.writer = writer;
        board.content = content;
        board.hidden = hidden;
        return board;
    }
    
    //==게시글 수정==//
    public void updateBoard(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
    
    //==게시글 삭제==//
    public void removeBoard() {
        this.hidden = 1.0;
    }
}
