package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLParagraphElement;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class ChannelController {

    @Autowired
    private ChannelService service;

    @GetMapping("/channel")
    public ResponseEntity<List<Channel>>showAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/channel/{channelCode}") // 한개의 채널 조회
    public ResponseEntity <Channel> show(@PathVariable  int channelCode){
        return ResponseEntity.status(HttpStatus.OK).body(service.show(channelCode));
    }


    @PostMapping("/channel")
    public ResponseEntity <Channel> create(@RequestBody  Channel channel){
        return ResponseEntity.status(HttpStatus.OK).body(service.create(channel));
    }

    @PutMapping("/channel")
    public ResponseEntity<Channel> update(@RequestBody  Channel channel){
        if(service.update(channel)!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(service.update(channel));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/channel/{id}")
    public ResponseEntity<Channel> delete(@PathVariable  int id){
      return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
