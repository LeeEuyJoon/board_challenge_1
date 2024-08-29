package com.board.board_week1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostDaoTest {

    @Autowired
    private PostDao postDao;

    @Test
    void insert() {
        // 1단계 데이터 선택 -> 임의의 postId로 새로운 게시글 생성
        // 1-1 단계 : 데이터 초기화
        postDao.deleteAll();

        // 1-2 단계 : 임의의 PostDto 생성
        PostDto postDto = PostDto.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("Test Writer")
                .build();

        // 2단계 데이터 처리 -> 삽입
        int cntInserted = postDao.insert(postDto);

        // 3단계 검증 -> 잘 삽입 되었으면 1 반환
        assertEquals(1, cntInserted);
    }

    @Test
    void deleteAll() {
        // 1단계 데이터 선택 -> 임의로 삽입된 2개의 post dto
        // 1-1 단계 : 데이터 비우기
        postDao.deleteAll();

        // 1-2 단계 : 게시글 dto 삽입
        PostDto postDto1 = PostDto.builder()
                .title("Title1")
                .content("Content1")
                .writer("Writer1")
                .build();

        PostDto postDto2 = PostDto.builder()
                .title("Title2")
                .content("Content2")
                .writer("Writer2")
                .build();

        postDao.insert(postDto1);
        postDao.insert(postDto2);

        int beforeDeleted = postDao.count();

        // 2단계 데이터 처리 -> 삽입된 임의의 dto 2개 deleteAll()로 삭제
        int deleteCnt = postDao.deleteAll();

        // 3단계 검증 -> deleteAll() 호출 전 count(beforeDeleted)와 삭제된 레코드 개수 (deleteCnt) 비교
        assertEquals(beforeDeleted, deleteCnt);
    }

    @Test
    void deletePostById() {
        // 1단계 데이터 선택 -> 임의의 dto
        // 1-1 단계 : 데이터 초기화
        postDao.deleteAll();

        // 1-2 단계 : 임의의 PostDto 생성 후 삽입
        PostDto postDto = PostDto.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("Test Writer")
                .build();

        postDao.insert(postDto);
        int postId = postDto.getPostId(); // 삽입된 게시물의 ID를 가져옴

        // 2단계 데이터 처리 -> id가 특정한 postId인 게시글 삭제
        int deletedCnt = postDao.deleteById(postId);

        // 3단계 검증 -> 잘 삭제 되었으면 1 반환
        assertEquals(1, deletedCnt);
    }

    @Test
    void updatePost() {
        // 1단계 데이터 선택 -> 임의의 dto
        // 1-1 단계 : 데이터 초기화
        postDao.deleteAll();

        // 1-2 단계 : 임의의 PostDto 생성 후 삽입
        PostDto postDto = PostDto.builder()
                .title("Original Title")
                .content("Original Content")
                .writer("Test Writer")
                .build();

        postDao.insert(postDto);
        int postId = postDto.getPostId();

        // 2단계 데이터 처리 -> 업데이트
        // 2-1 단계 : 업데이트 할 내용을 반영한 dto 생성
        PostDto updatedPostDto = PostDto.builder()
                .postId(postId)
                .title("Updated Title")
                .content("Updated Content")
                .writer("Test Writer")
                .build();

        // 2-2 단계 : 업데이트
        int updateCnt = postDao.update(updatedPostDto);

        // 3단계 검증 -> 업데이트가 성공하면 1 반환, 제목과 내용이 업데이트된 상태인지 확인
        assertEquals(1, updateCnt);
        PostDto selectedPost = postDao.selectById(postId);
        assertEquals("Updated Title", selectedPost.getTitle());
        assertEquals("Updated Content", selectedPost.getContent());
    }

    @Test
    void selectAll() {
        // 1단계 데이터 선택 -> 임의로 여러 게시물 삽입
        // 1-1 단계 : 데이터 초기화
        postDao.deleteAll();

        // 1-2 단계 : 게시글 dto 삽입
        PostDto postDto1 = PostDto.builder()
                .title("Title1")
                .content("Content1")
                .writer("Writer1")
                .build();

        PostDto postDto2 = PostDto.builder()
                .title("Title2")
                .content("Content2")
                .writer("Writer2")
                .build();

        postDao.insert(postDto1);
        postDao.insert(postDto2);

        // 2단계 데이터 처리 -> 전체 게시글 조회
        List<PostDto> posts = postDao.selectAll();

        // 3단계 검증 -> 삽입된 게시글 수와 조회된 게시글 수가 같으면 통과
        assertEquals(2, posts.size());
    }

    @Test
    void plusView() {
        // 1단계 데이터 선택 -> 임의의 게시글 생성
        // 1-1 단계 : 데이터 초기화
        postDao.deleteAll();

        // 1-2 단계 : 임의의 게시글 dto 생성 후 삽입
        PostDto postDto = PostDto.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("Test Writer")
                .build();

        postDao.insert(postDto);
        int postId = postDto.getPostId();

        // 2단계 데이터 처리 -> 조회수 증가
        int updateCnt = postDao.plusView(postDto);

        // 3단계 검증 -> 업데이트가 성공하면 1 반환, 조회수가 1 증가했는지 확인
        assertEquals(1, updateCnt);
        PostDto selectedPost = postDao.selectById(postId);
        assertEquals(1, selectedPost.getViewCnt());
    }
}