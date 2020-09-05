package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.RateNotification;
import com.solo.kinocavern.entity.User;

import java.util.List;

public interface NotificationService {

    public void sendRateNotificationsToSubscribers(Movie movie, User currentUser, int rate);

    public void sendChatNotification(User userFrom, User currentTo);

    public Boolean checkIfUserIdHasNewNotifications(Long currentUserId);
}
