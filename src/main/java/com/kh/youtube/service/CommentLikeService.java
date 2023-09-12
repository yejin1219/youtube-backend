package com.kh.youtube.service;

import com.kh.youtube.domain.CommentLike;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.CommentLikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentLikeService {

    @Autowired
    private CommentLikeDAO dao;

    public List<CommentLike> showAll(){
        return dao.findAll();
    }

    public CommentLike show(int commlikeCode){
        CommentLike commentLike = dao.findById(commlikeCode).orElse(null);
        return commentLike;
    }
    public CommentLike create (CommentLike vo){
        return dao.save(vo);

    }

    public CommentLike update(CommentLike vo){
        CommentLike target = dao.findById(vo.getCommlikeCode()).orElse(null);
        if(target!=null){
            return dao.save(vo);
        }
        return  null;
    }

    public CommentLike delete(int id) {
        CommentLike target = dao.findById(id).orElse(null);

            dao.delete(target);
            return target;


    }

}
