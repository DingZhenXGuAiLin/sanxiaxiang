package com.example.end_0.Service;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Result addComment(Comment comment);

    List<Comment> getCommentOfTourist(Integer tourist_id);

    List<Comment> getCommentOfLandscape(Integer landscape_id);

    List<Comment> getAllComment();

    Result deleteComment(Integer tourist_id, Integer landscape_id);

    Result updateComment(Comment comment);
}
