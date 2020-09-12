package com.solo.kinocavern.dao;


import com.solo.kinocavern.entity.Comment;

public interface CommentDAO {

    public void save(Comment comment);

    public void deleteById(Long id);

}
