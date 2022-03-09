package com.mountain.place.domain.comment.dao;


import com.mountain.place.domain.comment.model.Comment;
import com.mountain.place.domain.community.model.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {


    Page<Comment> findByCommuNo(Community community, Pageable pageable); // Entity에서 Community = CommuNo

}