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
//@Table(name="COMMENT_LIKE")
@Entity
@DynamicInsert
public class CommentLike {
    @Id
    @Column(name="COMM_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commentlikeSequence")
    @SequenceGenerator(name="commentlikeSequence", sequenceName = "SEQ_COMMENT_LIKE", allocationSize=1)
    private int commlikeCode;

    @Column(name="COMM_LIKE_DATE")
    private Date commlikeDate;

    @ManyToOne
    @JoinColumn(name="COMMENT_CODE")
    private  VideoComment commentCode;

    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
}
