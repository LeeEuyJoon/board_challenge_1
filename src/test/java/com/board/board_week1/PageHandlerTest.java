package com.board.board_week1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PageHandlerTest {

    @Test
    public void test1() {
        PageHandler ph = new PageHandler(250, 1);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
    }

    @Test
    public void test2() {
        PageHandler ph = new PageHandler(250, 11);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
    }

    @Test
    public void test3() {
        PageHandler ph = new PageHandler(255, 25);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 21);
        assertTrue(ph.getEndPage() == 26);
    }

    @Test
    public void test4() {
        PageHandler ph = new PageHandler(255, 10);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
    }
}