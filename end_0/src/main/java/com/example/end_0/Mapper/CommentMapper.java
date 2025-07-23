package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (tourist_id,landscape_id,content,time)" +
            "values (#{tourist_id},#{landscape_id},#{content},#{time})")
    void addComment(Comment comment);

    @Select("select * from comment where tourist_id=#{tourist_id}")
    List<Comment> getCommentOfTourist(Integer tourist_id);

    @Select("select * from comment where landscape_id=#{landscape_id}")
    List<Comment> getCommentOfLandscape(Integer landscape_id);

    @Select("select * from comment")
    List<Comment> getAllComment();

    @Delete("delete from comment where tourist_id = #{tourist_id} and landscape_id = #{landscape_id}")
    void deleteComment(Integer tourist_id, Integer landscape_id);

    @Update("update comment set content = #{content}, time = #{time} " +
            " where tourist_id = #{tourist_id} and landscape_id = #{landscape_id}")
    void updateComment(Comment comment);
}
