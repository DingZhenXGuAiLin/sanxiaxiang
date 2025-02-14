package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Comment;
import com.example.end_0.Service.CommentService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/getCommentOfLandscape")
    public List<Comment> getCommentOfLandscape(@RequestParam Integer landscape_id) {
        return commentService.getCommentOfLandscape(landscape_id);
    }

    @GetMapping("/getCommentOfTourist")
    public List<Comment> getCommentOfTourist(@RequestParam Integer tourist_id) {
        return commentService.getCommentOfTourist(tourist_id);
    }

    @GetMapping("/getAllComment")
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }

    @DeleteMapping("/deleteComment")
    public Result deleteComment(@RequestParam Integer tourist_id,@RequestParam Integer landscape_id) {
        return commentService.deleteComment(tourist_id,landscape_id);
    }

    @PutMapping("/updateComment")
    public Result updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

}
