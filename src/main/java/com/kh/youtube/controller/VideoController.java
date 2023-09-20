package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.service.CommentLikeService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoLikeService;
import com.kh.youtube.service.VideoService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
@Log4j2
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class VideoController {

    @Value("${spring.servlet.multipart.location}") //application.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private VideoService videoservice;

    @Autowired
    private VideoCommentService videocomment;

    @Autowired
    private VideoLikeService videolike;

    @Autowired
    private CommentLikeService commentlike;


    // 영상 전체 조회 Get - http://localhost:8080/api/video
    @GetMapping("/video")
    public ResponseEntity<List<Video>> showAllVideo() {
        return ResponseEntity.status(HttpStatus.OK).body(videoservice.showAll());
    }

    // 영상 추가 POST - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, String desc, String categoryCode) {
        log.info("video : " + video);
        log.info("image" + image);
        log.info("title" + title);
        log.info("desc" + desc);
        log.info("categoryCode" + categoryCode);
        //video_title, video_desc, video_url, video_photo, category_code

        // 업로드 처리
        // 1.비디오 : 비디오의 실제 파일 이름
        String originalVideo = video.getOriginalFilename();
        String realVideo = originalVideo.substring(originalVideo.lastIndexOf("\\")+1);
        log.info("realVideo : "+realVideo);

        //UUID
        String uuid = UUID.randomUUID().toString();

        // 실제로 저장할 파일 명(위치포함)
        String saveVideo = uploadPath + File.separator + uuid + "_" + realVideo;
        Path pathVideo = Paths.get(saveVideo);

        // 2. 이미지 처리
        String originalImg = image.getOriginalFilename();
        String realImg = originalImg.substring(originalImg.lastIndexOf("\\")+1);

        String saveImg = uploadPath + File.separator + uuid + "_"+ realImg;
        Path pathImg = Paths.get(saveImg);

        try {
            video.transferTo(pathVideo);
            image.transferTo(pathImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Video vo = new Video();
        vo.setVideoUrl(saveVideo);
        vo.setVideoPhoto(saveImg);
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);

        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setCategory(category);

        Channel channel = new Channel();
        channel.setChannelCode(21);
        vo.setChannel(channel);

        Member member = new Member();
        member.setId("user2");
        vo.setMember(member);


       // return ResponseEntity.status(HttpStatus.OK).build();
     return ResponseEntity.status(HttpStatus.OK).body(videoservice.create(vo));





    }

    // 영상 수정 PUT - http://localhost:8080/api/video
    @PutMapping("/video")
    public ResponseEntity<Video> updateVideo(@RequestBody Video vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoservice.update(vo));
    }

    // 영상 삭제  DELETE -http://localhost:8080/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity<Video> deleteVideo(@PathVariable int videoCode) {
        return ResponseEntity.status(HttpStatus.OK).body(videoservice.delete(videoCode));
    }

    // 영상 1개 조회 GET - http://localhost:8080/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity<Video> showVideo(@PathVariable int videoCode) {
        return ResponseEntity.status(HttpStatus.OK).body(videoservice.show(videoCode));
    }

    // 영상 1개에 따른 댓글 전체 조회  GET - http://localhost:8080/api/video/1/comment
    // VideoCommentDAO에 작성 SELECT * FROM VIDEO_COMMENT WHERE VIDEO_CODE = ?
    @GetMapping("video/{id}/comment")
    public ResponseEntity<List<VideoComment>> videoCommentList(@PathVariable int code) {
        return ResponseEntity.status(HttpStatus.OK).body(videocomment.findByVideoCode(code));
    }

    // 좋아요 추가 POST - http://localhost:8080/api/video/like
    @PostMapping("video/like")
    public ResponseEntity<VideoLike> createVideoLike(@RequestBody VideoLike vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videolike.create(vo));
    }

    // 좋아요 취소 DELETE http://localhost:8080/api/video/like/1
    @DeleteMapping("/video/like/{id}")
    public ResponseEntity<VideoLike> deleteVideoLike(@PathVariable int vlikeCode) {
        return ResponseEntity.status(HttpStatus.OK).body(videolike.delete(vlikeCode));
    }

    // 댓글 추가 POST http://localhost:8080/api/video/comment
    @PostMapping("/video/comment")
    public ResponseEntity<VideoComment> createVideoComment(@RequestBody VideoComment vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videocomment.create(vo));
    }

    //댓글 수정 PUT http://localhost:8080/api/video/comment
    @PutMapping("/video/comment")
    public ResponseEntity<VideoComment> updateVideoComment(@RequestBody VideoComment vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videocomment.update(vo));
    }


    //댓글 삭제 DELETE http://localhost:8080/api/video/comment/1
    @DeleteMapping("/video/comment/{id}")
    public ResponseEntity<VideoComment> deleteVideoComment(@PathVariable int commentCode){
            return ResponseEntity.status(HttpStatus.OK).body(videocomment.delete(commentCode));
    }

    // 댓글 좋아요 추가 POST http://localhost:8080/api/video/comment/like
    @PostMapping("/video/comment/like")
    public ResponseEntity<CommentLike> createCommentLike(@RequestBody CommentLike vo) {
        return ResponseEntity.status(HttpStatus.OK).body(commentlike.create(vo));
    }
    //댓글 좋아요 취소  DELETE http://localhost:8080/api/video/comment/like/1
    @DeleteMapping("/video/comment/like/{id}")
    public ResponseEntity<CommentLike> deleteCommentLike(@PathVariable int commentCode){
        return ResponseEntity.status(HttpStatus.OK).body(commentlike.delete(commentCode));
    }
}
