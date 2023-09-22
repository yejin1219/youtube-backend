package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.service.CommentLikeService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoLikeService;
import com.kh.youtube.service.VideoService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.StyledEditorKit;
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

    @Value("${youtube.upload.path}") //application.properties에 있는 변수
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
    @GetMapping("/video")                      // defaultValue1로 해야 page-1 = 0, 디폴트값 지정해줘야 포스트맨에서 파라미터 값 안넘겨도(http://localhost:8080/api/video?page=2)이렇게 안넘겨도 영상 전체 조회 할 수 있도록 
    public ResponseEntity<List<Video>> showAllVideo(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="category", required = false) Integer category) {
                                                                                                                                                             // int가 아닌 Integer 로 지정하면 null 값도 받을 수 잇음
        // 정렬
        Sort sort = Sort.by("videoCode").descending();

        // 한 페이지의 10개 동영상 보여지도록..      // 페이지넘버가 바껴야 하기 때문에 파라미터 값으로 받아와야 함
        Pageable pageable = PageRequest.of(page-1,20, sort); //페이지는 0부터 시작 ,10개


        // 동적 쿼리를 위한 QuerDSL을 사용한 코드들 추가
        //1. Q도메인 클래스(build gradle를 통해 만들어진 거)를 가져와야 한다.

        QVideo qvideo = QVideo.video;

        //2. BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();


        if(category!=null){
            log.info("category =======> " + category);
            //3. 원하는 조건은 필드값과 같이 결합해서 생성한다.
            BooleanExpression expression = qvideo.category.categoryCode.eq(category);

            //4. 만들어진 조건은 where문에 and나 or 같은 키워드와 결합한다.
            builder.and(expression);
        }

        Page<Video> result= videoservice.showAll(pageable, builder);

        // result안에는 많은 정보들이 담겨져 있다.
        log.info("Total Pages : " +  result.getTotalPages());// 총 몇 페이지
        log.info("Total Count : " + result.getTotalElements()); // 전체 갯수
        log.info("Page Number : " + result.getNumber()); // 현재 페이지 번호
        log.info("Page Size:" + result.getSize());// 페이지당 데이터 개수
        log.info("Next Page : " + result.hasNext()); // 다음 페이지가 있는지 존재 여부
        log.info("First Page : " + result.isFirst()); // 시작 페이지 여부

        //return ResponseEntity.status(HttpStatus.OK).build();
       return ResponseEntity.status(HttpStatus.OK).body(result.getContent()); //  videoservice.showAll() 이걸로 보내면 반환값이 List가 아닌 Page이기 때문에 사용불가
    }

    // 영상 추가 POST - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title,@RequestParam(name="desc", required = false) String desc, String categoryCode) {
        log.info("video : " + video);                                            // 필수값이 아닌 것들은 required = false 로 지정해줘서  값을 안넣어도 상관이없도록
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
        vo.setVideoUrl( uuid + "_" + realVideo);
        vo.setVideoPhoto(uuid + "_"+ realImg);
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);

        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setCategory(category);

        Channel channel = new Channel();
        channel.setChannelCode(31);
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
