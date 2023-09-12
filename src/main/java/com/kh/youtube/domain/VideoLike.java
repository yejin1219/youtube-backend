package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
public class VideoLike {

    @Id
    @Column(name="V_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY , generator = "videoLikeSequence")
    @SequenceGenerator(name="videoLikeSequence", sequenceName = "SEQ_VIDEO_LIKE", allocationSize = 1)
    private int vlikeCode;

    @Column(name="V_LIKE_DATE")
    private Date vlikeDate;

    @ManyToOne
    @JoinColumn(name="VIDEO_CODE")
    private Video video;

    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
}
