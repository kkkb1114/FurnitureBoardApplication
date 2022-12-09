package com.example.FurnitureBoardApplication.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id; // pk
    private String title; // 제목
    private String writer; // 작성자
    private String content; // 내용
    private Long views; // 조회수
    private String createdDate; // 작성일 (상속받은 작성일 시간은 분 초까지 나와서 따로 만들었다.)
    private Double hidden;

    protected Board() {
    }

    //==생성 메서드==//
    public static Board createBoard(String title, String writer, String content, Long views, Double hidden) {
        Board board = new Board();
        board.title = title;
        board.writer = writer;
        board.content = content;
        board.views = views;
        board.hidden = hidden;
        return board;
    }

    //==생성 시간 설정==//
    public void setCreatedDate(LocalDateTime createdDateTime){
        this.createdDate = createdDateTime.toString().split("T")[0];
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

    //==게시글 조회수 증가==//
    public void addViewsBoard() {
        this.views++;
    }
}
