package com.kh.youtube.service;

import com.kh.youtube.domain.CommentLike;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoCommentDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VideoCommentService {

    @Autowired
    private VideoCommentDAO dao;



    public List<VideoComment> showAll(){
       return dao.findAll();
    }

    public VideoComment show(int commentCode){
        VideoComment videocomment = dao.findById(commentCode).orElse(null);
        return videocomment;
    }

    public VideoComment create(VideoComment videocomment){
//        Member member = memberdao.findById(videocomment.getMember().getId()).orElse(null);
//        videocomment.setMember(member);
        return dao.save(videocomment);

    }

    public VideoComment update(VideoComment videocomment){
        VideoComment target = dao.findById(videocomment.getCommentCode()).orElse(null);
        if(target!=null){
            return dao.save(videocomment);
        }
        return null;
    }

    public VideoComment delete(int commentCode){
        VideoComment target = dao.findById(commentCode).orElse(null);

        dao.delete(target);
        return target;
    }

    public List<VideoComment> findByVideoCode(int code){
        return dao.findByVideoCode(code);
    }
}
