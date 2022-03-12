package com.mountain.place.domain.comment.dao;


import com.mountain.place.domain.comment.model.Comment;
import com.mountain.place.domain.community.model.Community;
import com.mountain.place.domain.mountain.model.Mountain;
import com.mountain.place.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {


    Page<Comment> findByCommuNo(Community community, Pageable pageable); // Entity에서 Community = CommuNo

    Page<Comment> findByMountainNo(Mountain mountain, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);

    //Page<Comment> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}