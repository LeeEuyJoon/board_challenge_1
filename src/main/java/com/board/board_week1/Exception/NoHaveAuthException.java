package com.board.board_week1.Exception;

import com.board.board_week1.PostDto;
import lombok.Getter;

@Getter
public class NoHaveAuthException extends RuntimeException {
    private PostDto post;

    public NoHaveAuthException(String msg) {
        super(msg);
    }

    public NoHaveAuthException(String msg, PostDto post) {
        super(msg);
        this.post = post;
    }
}
