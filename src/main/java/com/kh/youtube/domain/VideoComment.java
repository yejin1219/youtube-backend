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
public class VideoComment {
    @Id
    @Column(name="COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoCommSequence")
    @SequenceGenerator(name = "videoCommSequence" , sequenceName = "SEQ_VIDEO_COMMENT", allocationSize = 1)
    private int commentCode;

    @Column(name="COMMENT_DESC")
    private String commentDesc;
    @Column(name="COMMENT_DATE")
    private Date commentDate;
    @Column(name="COMMENT_PARENT")
    private String commentParent;

    @ManyToOne
    @JoinColumn(name="VIDEO_CODE")
    private Video video;

    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
}
