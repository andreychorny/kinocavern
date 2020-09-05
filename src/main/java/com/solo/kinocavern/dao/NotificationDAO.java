package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Notification;

import java.util.List;

public interface NotificationDAO {

    public void save(Notification notification);

    public Boolean checkIfUserIdHasNewNotifications(Long currentUserId);
}
