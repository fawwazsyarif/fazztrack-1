package com.payfazz.hello.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import com.payfazz.hello.model.CommentModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.CommentDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.CommentService;
import com.payfazz.hello.service.ObjectiveService;
import com.payfazz.hello.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    CommentDB commentDb;

    @Autowired
    private ObjectiveService objectiveService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/objective/{id}/success-add-comment")
    public BaseResponse<CommentModel> addComment(@PathVariable(value = "id") long idObjective,
            @RequestBody CommentModel comment, BindingResult bindingResult) {
        BaseResponse<CommentModel> response = new BaseResponse<CommentModel>();

        ObjectiveModel temp = objectiveService.getObjectiveDetailById(idObjective);
        Date date = new Date();

        comment.setId(comment.getId());
        comment.setCommentObjective(temp);
        comment.setDate(new Timestamp(date.getTime()));
        comment.setDescription(comment.getDescription());
        comment.setEdited(false);

        comment.setCommenter(comment.getCommenter());

        commentService.addComment(comment);
        response.setStatus(200);
        response.setMessage("Succeess");
        response.setResult(comment);

        return response;
    }

    @PostMapping(value = "/objective/{id}/success-edit-comment")
    public BaseResponse<CommentModel> editComment(@PathVariable(value = "id") long idObjective,
            @RequestBody CommentModel comment, BindingResult bindingResult) {
        BaseResponse<CommentModel> response = new BaseResponse<CommentModel>();

        ObjectiveModel temp = objectiveService.getObjectiveDetailById(idObjective);
        Date date = new Date();

        comment.setId(comment.getId());
        comment.setCommentObjective(temp);
        comment.setDate(new Timestamp(date.getTime()));
        comment.setDescription(comment.getDescription());
        comment.setEdited(true);
        comment.setCommenter(comment.getCommenter());

        commentService.addComment(comment);
		response.setStatus(200);
		response.setMessage("Succeess");
		response.setResult(comment);

        return response;
    }

    @PostMapping(value="/objective/{id}/success-delete-comment")
    public BaseResponse<CommentModel> deleteComment(@RequestBody CommentModel comment, BindingResult bindingResult) {
        BaseResponse<CommentModel> response = new BaseResponse<CommentModel>();

        commentService.removeById(comment.getId());
        
        response.setStatus(200);
		response.setMessage("Succeess");
		response.setResult(null);

        return response;
    }
    
}
