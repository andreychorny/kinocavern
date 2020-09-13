package com.solo.kinocavern.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solo.kinocavern.service.NotificationService;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Column(name="is_unseen", columnDefinition = "TINYINT(1)")
    private boolean isUnseen;

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUnseen() {
        return isUnseen;
    }

    public void setUnseen(boolean unseen) {
        isUnseen = unseen;
    }
}
