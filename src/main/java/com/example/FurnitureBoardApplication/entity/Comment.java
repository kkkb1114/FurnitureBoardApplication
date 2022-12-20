package com.example.FurnitureBoardApplication.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String writer; // 작성자
    private String content; // 내용
    private String createdDate; // 작성일 (상속받은 작성일 시간은 너무 상세하게 나와서 따로 만들었다.)
    private Double hidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 게시글

    protected Comment(){}

    public static Comment createComment(String writer, String content, Double hidden, Member member, Board board){
        Comment comment = new Comment();
        comment.writer = writer;
        comment.content = content;
        comment.hidden = hidden;
        comment.member = member;
        comment.board = board;
        return comment;
    }

    //==생성 시간 설정==//
    public void setCreatedDate(LocalDateTime createdDateTime){
        String createdDate = createdDateTime.toString().split("T")[0];
        String createdTime = createdDateTime.toString().split("T")[1].split("\\.")[0];
        this.createdDate = createdDate +" "+ createdTime;
    }

    //==댓글 수정==//
    public void updateComment(String content){
        this.content = content;
    }

    //==댓글 제거==//
    public void removeComment(){
        this.hidden = 1.0;
    }
}
