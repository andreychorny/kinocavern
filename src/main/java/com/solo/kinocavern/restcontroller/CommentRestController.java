package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.entity.Comment;
import com.solo.kinocavern.dto.request.CommentFormWrapper;
import com.solo.kinocavern.service.CommentService;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentRestController {

    @Autowired
    public CommentService commentService;

    @Autowired
    public UserService userService;

    @PostMapping("/comments")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Comment addComment(HttpServletRequest request,
                              @RequestBody CommentFormWrapper commentFormWrapper)  {
        Long movieId = commentFormWrapper.getMovieId();
        String content = commentFormWrapper.getContent();
        Long commentParentId = commentFormWrapper.getCommentParentId();
        userService.addComment(request, movieId, content, commentParentId);

        return null;
    }

    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteComment(@PathVariable Long commentId)  {
        commentService.deleteById(commentId);
    }

}
