package com.kh.youtube.service;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.repo.ChannelDAO;
import com.kh.youtube.repo.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChannelService {

    @Autowired
    private ChannelDAO dao;

    @Autowired
    private MemberDAO memberDAO;

    public List<Channel> showAll(){
        return dao.findAll();
    }

    public Channel show(int id){
        log.info("상세조회 id : " + id);
        Channel channel = dao.findById(id).orElse(null);
        log.info("상세조회 : " + channel);
        return channel;
    }

    public Channel create(Channel channel){
        Member member = memberDAO.findById(channel.getMember().getId()).orElse(null);
        log.info("channel Create : " + member);
        channel.setMember(member);
        return dao.save(channel);
    }

    public Channel update(Channel channel){
        Channel target = dao.findById(channel.getChannelCode()).orElse(null);
        if(target!=null){
            return dao.save(channel);
        }
        return null;
    }

    public Channel delete(int id){
        Channel target =  dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    // 특정 멤버의 모든 채널 조회
    public List<Channel> showMember(String id){

        Member member = memberDAO.findById(id).orElse(null);
        List<Channel> target = dao.findByMemberId(member.getId());


        return target;
    }
}
