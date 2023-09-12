package com.kh.youtube.service;

import com.kh.youtube.domain.CommentLike;
import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.repo.CommentLikeDAO;
import com.kh.youtube.repo.SubscribeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscribeService {

    @Autowired
    private SubscribeDAO dao;


    public List<Subscribe> showAll(){
        return dao.findAll();
    }

    public Subscribe show(int subscode){
        Subscribe subscribe = dao.findById(subscode).orElse(null);
        return subscribe;
    }
    public Subscribe create (Subscribe vo){
        return dao.save(vo);

    }

    public Subscribe update(Subscribe vo){
        Subscribe target = dao.findById(vo.getSubscode()).orElse(null);
        if(target!=null){
            return dao.save(vo);
        }
        return  null;
    }

    public Subscribe delete(int subscode) {
        Subscribe target = dao.findById(subscode).orElse(null);

        dao.delete(target);
        return target;


    }

    public List<Subscribe> findByMemberId(String userId){
        return dao.finbyMemberId(userId);
    }
}
