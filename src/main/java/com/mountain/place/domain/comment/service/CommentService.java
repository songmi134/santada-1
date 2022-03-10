package com.mountain.place.domain.comment.service;

import com.mountain.place.controller.mountainComment.dto.RegisterMTCommentDTO;
import com.mountain.place.domain.category.dao.CategoryRepository;
import com.mountain.place.domain.category.model.Category;
import com.mountain.place.domain.comment.dao.CommentRespository;
import com.mountain.place.domain.comment.model.Comment;
import com.mountain.place.domain.community.dao.CommunityRepository;
import com.mountain.place.domain.community.model.Community;
import com.mountain.place.domain.mountain.dao.MountainRepository;
import com.mountain.place.domain.mountain.model.Mountain;
import com.mountain.place.domain.user.model.User;
import com.mountain.place.exception.CustomException;
import com.mountain.place.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LongSummaryStatistics;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentRespository commentRespository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    MountainRepository mountainRepository;


    @Transactional
    public Page<Comment> getCommunityCommentList(Community community, Pageable pageable) {
        return commentRespository.findByCommuNo(community, pageable);
    }


    @Transactional
    public void deleteCommunityComment(User user, Long commuPostNum, Long commentNo) {

        Community community =
                communityRepository.findById(commuPostNum)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITY));

        Comment comment =
                commentRespository.findById(commentNo)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPLY));


        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        } else if (!comment.getCommuNo().equals(community)) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAM);
        } else {
            commentRespository.delete(comment);
        }
    }

    @Transactional
    public Comment createMTComment(User user, Long mountainNo, RegisterMTCommentDTO registerMTCommentDTO) {
        Mountain mountain = mountainRepository.findById(mountainNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));

        Comment comment = Comment.builder()
                .commentContent(registerMTCommentDTO.getContent())
                .mountainNo(mountain)
                .user(user)
                .build();

        return commentRespository.save(comment);
    }

    @Transactional
    public void deleteMTComment(User user, Long mountainNo, Long commentNo) {

        Mountain mountain = mountainRepository.findById(mountainNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));

        Comment comment = commentRespository.findById(commentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPLY));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        } else if (!comment.getMountainNo().equals(mountain)) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAM);
        } else {
            commentRespository.delete(comment);
        }
    }


    @Transactional
    public void updateMTComment(User user, Long mountainNo, Long commentNo, RegisterMTCommentDTO registerMTCommentDTO) {
        Mountain mountain = mountainRepository.findById(mountainNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));

        Comment comment = commentRespository.findById(commentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPLY));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        } else if (!comment.getMountainNo().equals(mountain)) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAM);
        } else {
            comment.setCommentContent(registerMTCommentDTO.getContent());
            commentRespository.save(comment);
        }
    }



    @Transactional
    public Page<Comment> getMountainCommentList(Mountain mountain, Pageable pageable) {
        return commentRespository.findByMountainNo(mountain, pageable);
    }
}