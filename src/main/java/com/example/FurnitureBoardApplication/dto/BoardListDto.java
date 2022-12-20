package com.example.FurnitureBoardApplication.dto;

import com.example.FurnitureBoardApplication.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardListDto {
    private List<Board> boardList = new ArrayList<>();
    private Long totalPages;
    private Long totalCount;

    @Builder
    public BoardListDto(List<Board> boardList, Long totalPages, Long totalCount){
        this.boardList = boardList.stream()
                .map(Board::new).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }
}
