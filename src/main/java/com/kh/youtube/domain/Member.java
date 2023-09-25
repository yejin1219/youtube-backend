package com.kh.youtube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert //디폴트값이 있는 경우 명시 (디폴트값이 자동으로 들어감)
@Builder
public class Member {

    @Id // 프라이머리키는 Id로 명시
    private String id;

    @Column //나머진 Colum으로 명시
    private String password;
    @Column
    private String name;
    @Column
    private String authority;


}
