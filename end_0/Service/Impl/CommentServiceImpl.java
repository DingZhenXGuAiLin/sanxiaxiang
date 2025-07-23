package com.example.end_0.Service.Impl;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Mapper.CommentMapper;
import com.example.end_0.Pojo.entity.Comment;
import com.example.end_0.Service.CommentService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Result addComment(Comment comment) {
        commentMapper.addComment(comment);
        return Result.success();
    }

    @Override
    public List<Comment> getCommentOfTourist(Integer tourist_id) {
        return commentMapper.getCommentOfTourist(tourist_id);
    }

    @Override
    public List<Comment> getCommentOfLandscape(Integer landscape_id) {
        return commentMapper.getCommentOfLandscape(landscape_id);
    }

    @Override
    public List<Comment> getAllComment() {
        return commentMapper.getAllComment();
    }

    @Override
    public Result deleteComment(Integer tourist_id, Integer landscape_id) {
        commentMapper.deleteComment(tourist_id,landscape_id);
        return Result.success();
    }

    @Override
    public Result updateComment(Comment comment) {
        commentMapper.updateComment(comment);
        return Result.success();
    }
}
