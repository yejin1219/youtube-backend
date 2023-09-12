package com.kh.youtube.service;

import com.kh.youtube.domain.Video;
import com.kh.youtube.domain.VideoLike;
import com.kh.youtube.repo.MemberDAO;
import com.kh.youtube.repo.VideoDAO;
import com.kh.youtube.repo.VideoLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VideoLikeService {

    @Autowired
    private VideoLikeDAO dao;


    public List<VideoLike> showAll(){
        return dao.findAll();
    }

    public VideoLike show(int vlikeCode){
        VideoLike videolike = dao.findById(vlikeCode).orElse(null);
        return videolike;
    }

    public VideoLike create(VideoLike videolike) {
//      Member member =  memberdao.findById(video.getMember().getId()).orElse(null);
//      video.setMember(member);
        return dao.save(videolike);
    }

    public VideoLike update(VideoLike videolike){
        VideoLike target = dao.findById(videolike.getVlikeCode()).orElse(null);
        if(target!=null){
            return dao.save(videolike);
        }
        return null;
    }

    public VideoLike delete(int vlikeCode){
        VideoLike target =  dao.findById(vlikeCode).orElse(null);
        dao.delete(target);
        return target;
    }
}
