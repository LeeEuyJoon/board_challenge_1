package com.board.board_week1;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDto {
    Integer postId;
    String title;
    String content;
    String writer;
    Integer viewCnt;
    Date regDate;
    Date upDate;
}
