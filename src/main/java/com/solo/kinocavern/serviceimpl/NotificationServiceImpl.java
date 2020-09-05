package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.NotificationDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public void sendRateNotificationsToSubscribers(Movie movie, User currentUser, int rate){
        String message = currentUser.getUsername() + " rated " + movie.getTitle()+" with "
                + rate +" out of 10";
        for(User user: currentUser.getSubscribers()){
            RateNotification rateNotification = new RateNotification();
            rateNotification.setUnseen(true);
            rateNotification.setUser(user);
            rateNotification.setMovie(movie);
            rateNotification.setUserFrom(currentUser);
            rateNotification.setMessage(message);
            this.simpMessagingTemplate.convertAndSend("/notification-publisher/"+user.getId(), true);
            notificationDAO.save(rateNotification);
        }
    }

    @Override
    public void sendChatNotification(User userFrom, User userTo){
        String message = userFrom.getUsername()+ " send you a new message!";
        ChatNotification chatNotification = new ChatNotification();
        chatNotification.setUnseen(true);
        chatNotification.setMessage(message);
        chatNotification.setUser(userTo);
        chatNotification.setUserFrom(userFrom);
        this.simpMessagingTemplate.convertAndSend("/notification-publisher/"+userTo.getId(), true);
        notificationDAO.save(chatNotification);
    }

    @Override
    public Boolean checkIfUserIdHasNewNotifications(Long currentUserId){
        return notificationDAO.checkIfUserIdHasNewNotifications(currentUserId);
    }

}
