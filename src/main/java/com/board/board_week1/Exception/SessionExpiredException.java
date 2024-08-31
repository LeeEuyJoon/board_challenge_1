package com.board.board_week1.Exception;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String msg) {
        super(msg);
    }
}