package com.board.board_week1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void insert() {
        // 1단계 데이터 선택 -> id가 "IMSI"인 임의의 dto
            // 1-1 단계 : "IMSI" id 가지고 있는 데이터 삭제
        userDao.deleteById("IMSI");

            // 1-2 단계 : 임의의 dto 생성
        UserDto userDto1 = UserDto.builder()
                .userId("IMSI")
                .pwd("123")
                .build();

        // 2단계 데이터 처리 -> 삽입
        int cntInserted = userDao.insert(userDto1);

        // 3단계 검증 -> 잘 삽입 되었으면 1 반환
        assertEquals(1, cntInserted);
    }

    @Test
    void deleteAll() {
        // 1단계 데이터 선택 -> 임의로 삽입된 2개의 user dto
            // 1-1 단계 : 데이터 비우기
        userDao.deleteAll();

            // 1-2 단계 : 유저 dto 삽입
        UserDto userDto1 = UserDto.builder()
                .userId("IMSI1")
                .pwd("123")
                .build();

        UserDto userDto2 = UserDto.builder()
                .userId("IMSI1")
                .pwd("123")
                .build();


        userDao.insert(userDto1);
        userDao.insert(userDto2);


        int beforeDeleted = userDao.count();

        // 2단계 데이터 처리 -> 삽입된 임의의 dto 2개 deleteAll()로 삭제
        int deleteCnt = userDao.deleteAll();

        // 3단계 검증 -> deleteAll() 호출 전 count(beforeDeleted)와 삭제된 레코드 개수 (deleteCnt) 비교
        assertEquals(beforeDeleted, deleteCnt);

    }

    @Test
    void deleteUserById() {
        // 1단계 데이터 선택 -> 임의의 dto
            // 1-1 단계 : 데이터 비우기
        userDao.deleteAll();

            // 1-2 단계 : 임의의 유저 dto 생성 후 삽입
        UserDto userDto1 = UserDto.builder()
                .userId("IMSI")
                .pwd("123")
                .build();

        userDao.insert(userDto1);

        // 2단계 데이터 처리 -> id가 "IMSI"인 유저 삭제
        int deletedCnt = userDao.deleteById("IMSI");

        // 3단계 검증 -> 잘 삭제 되었으면 1 반환
        assertEquals(1, deletedCnt);
    }



    @Test
    void updatePwd() {
        // 1단계 데이터 선택 -> 임의의 dto
            // 1-1 단계 : 데이터 비우기
        userDao.deleteById("IMSI");

            // 1-2 단계 : 임의의 유저 dto 생성 후 삽입
        UserDto userDto1 = UserDto.builder()
                .userId("IMSI")
                .pwd("123")
                .build();

        userDao.insert(userDto1);

        // 2 단계 데이터 처리 -> 업데이트
            // 2-1 단계 : 업데이트 할 내용을 반영한 dto 생성
        UserDto userDto = UserDto.builder()
                .userId("IMSI")
                .pwd("321")
                .build();

            // 2-2 단계 : 업데이트
        userDao.update(userDto);

        // 3단계 검증 -> IMSI 유저의 비번이 321이면 통과
        assertEquals("321", userDao.selectById("IMSI").getPwd());
    }
}