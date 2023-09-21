package com.kh.youtube.service;

import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Video;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoDAO dao;

    public Page<Video> showAll(Pageable pageable){
        // dao.findAll() -> List<Video>
        //dao.findAll(pageable) -> Page<Video> 로 반환
        return dao.findAll(pageable);
    }

    public Video show(int videoCode){
        Video video = dao.findById(videoCode).orElse(null);
        return video;
    }

    public Video create(Video video) {

        return dao.save(video);
    }

    public Video update(Video video){
        Video target = dao.findById(video.getVideoCode()).orElse(null);
        if(target!=null){
            return dao.save(video);
        }
        return null;
    }

    public Video delete(int videoCode){
       Video target =  dao.findById(videoCode).orElse(null);
        dao.delete(target);
        return target;
    }

    // 채널 별 목록보기
    public List<Video> findByChannelCode(int code){
        return dao.findByChannelCode(code);
    }


}
