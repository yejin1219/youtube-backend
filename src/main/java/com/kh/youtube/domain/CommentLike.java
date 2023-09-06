package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike {
    private int commlikeCode;
    private Date commlikeDate;
    private int commentCode;
    private Member member;
}
