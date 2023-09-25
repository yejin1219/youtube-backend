package com.kh.youtube.repo;


import com.kh.youtube.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

                                   // 1.extends JpaRepository , 2. 제네릭<Entity 객체, 프라이머리키 타입>
public interface MemberDAO extends JpaRepository<Member , String> {

    Member findByIdAndPassword(String id, String password);
}
