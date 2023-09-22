package com.kh.youtube.repo;

import com.kh.youtube.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface VideoDAO extends JpaRepository<Video,Integer>, QuerydslPredicateExecutor<Video> {
     // 채널 별 목록보기
    // SELECT * FROM VIDEO WHERE CHANNEL_CODE=?
    @Query(value = "SELECT * FROM VIDEO WHERE CHANNEL_CODE= :code", nativeQuery = true)
    List<Video> findByChannelCode(int code);
}
