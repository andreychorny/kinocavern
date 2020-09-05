package com.solo.kinocavern.entity;

import javax.persistence.*;

@Entity
@Table(name = "chat_notification")
public class ChatNotification extends Notification{

    @ManyToOne(fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_from_id")
    private User userFrom;

    @Column(name="message")
    private String message;

    public ChatNotification() {
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
