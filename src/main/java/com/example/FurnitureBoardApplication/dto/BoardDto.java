package com.example.FurnitureBoardApplication.dto;

import com.example.FurnitureBoardApplication.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String writer;
    private String content;
    private Long views;
    private String createdDate;

    public BoardDto(Board board){
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createdDate = board.getCreatedDate();
    }
}
