package com.example.FurnitureBoardApplication.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long boardId; // pk
    private String title; // 제목
    private String writer; // 작성자
    private String content; // 내용
    private Long views; // 조회수
    private String createdDate; // 작성일 (상속받은 작성일 시간은 너무 상세하게 나와서 따로 만들었다.)
    private Double hidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>(); // 댓글

    public Board(Board board) {
    }

    //==생성 메서드==//
    public static Board createBoard(String title, String writer, String content, Long views, Double hidden, Member member) {
        Board board = new Board();
        board.title = title;
        board.writer = writer;
        board.content = content;
        board.views = views;
        board.hidden = hidden;
        board.member = member;
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
