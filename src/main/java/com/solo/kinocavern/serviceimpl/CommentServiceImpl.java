package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.CommentDAO;
import com.solo.kinocavern.entity.Comment;
import com.solo.kinocavern.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentDAO commentDAO;

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

}
