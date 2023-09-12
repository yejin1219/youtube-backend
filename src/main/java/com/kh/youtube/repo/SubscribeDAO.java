package com.kh.youtube.repo;

import com.kh.youtube.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeDAO extends JpaRepository<Subscribe, Integer> {
    // 내가 구독한 채널 조회 SELECT * FROM WHERE ID=?
    @Query(value = "SELECT * FROM WHERE ID= :id", nativeQuery = true)
    List<Subscribe> finbyMemberId(String userId);
}
