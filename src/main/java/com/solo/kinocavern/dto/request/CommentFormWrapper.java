package com.solo.kinocavern.dto.request;

public class CommentFormWrapper {

    private Long movieId;
    private String content;
    private Long commentParentId;

    public CommentFormWrapper() {
    }

    public CommentFormWrapper(Long movieId, String content, Long commentParentId) {
        this.movieId = movieId;
        this.content = content;
        this.commentParentId = commentParentId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Long commentParentId) {
        this.commentParentId = commentParentId;
    }
}
