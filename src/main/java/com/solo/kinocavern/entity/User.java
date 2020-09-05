package com.solo.kinocavern.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id")
        private Long id;

        @NotBlank
        @Size(min = 6, max = 20)
        @Column(name="username")
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        @Column(name="email")
        @JsonIgnore
        private String email;

        @NotBlank
        @Size(min = 6 ,max = 60)
        @Column(name="password")
        @JsonIgnore
        private String password;

        @ManyToOne
        @JoinColumn(name = "role_id",referencedColumnName="id")
        @JsonIgnore
        private Role role;

        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        @JsonIgnore
        private Set<Rating> ratings = new HashSet<>();

        @OneToMany(fetch=FetchType.LAZY, mappedBy="user",
                cascade=CascadeType.ALL)
        @JsonIgnore
        private List<Notification> notifications;

        @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
        @JoinTable(
                name="wishlist",
                joinColumns=@JoinColumn(name="user_id"),
                inverseJoinColumns=@JoinColumn(name="movie_id")
        )
        @JsonIgnore
        private Set<Movie> wishlist = new HashSet<>();

        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        @JsonIgnore
        private Set<Comment> comments = new HashSet<>();

        @ManyToMany
        @JoinTable(name="subscription",
                joinColumns=@JoinColumn(name="user_id_from"),
                inverseJoinColumns=@JoinColumn(name="user_id_to")
        )
        @JsonIgnore
        private Set<User> subscriptions = new HashSet<>();

        @ManyToMany
        @JoinTable(name="subscription",
                joinColumns=@JoinColumn(name="user_id_to"),
                inverseJoinColumns=@JoinColumn(name="user_id_from")
        )
        @JsonIgnore
        private Set<User> subscribers = new HashSet<>();

        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "userFrom",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        @JsonIgnore
        private List<Chat> chatMessagesSent = new ArrayList<>();

        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "userTo",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        @JsonIgnore
        private List<Chat> chatMessagesReceived = new ArrayList<>();

        public User() {
        }

        public User(Long id, String username, String email, String password, Role role) {
                this.id = id;
                this.username = username;
                this.email = email;
                this.password = password;
                this.role = role;
        }

        public User(String username, String email, String password) {
                this.username = username;
                this.email = email;
                this.password = password;
        }


        public Set<Rating> getRatings() {
                return ratings;
        }

        public void setRatings(Set<Rating> ratings) {
                this.ratings = ratings;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }


        public Set<Comment> getComments() {
                return comments;
        }

        public void setComments(Set<Comment> comments) {
                this.comments = comments;
        }

        public Set<Movie> getWishlist() {
                return wishlist;
        }

        public void setWishlist(Set<Movie> wishlist) {
                this.wishlist = wishlist;
        }

        public Set<User> getSubscriptions() {
                return subscriptions;
        }

        public void setSubscriptions(Set<User> subscriptions) {
                this.subscriptions = subscriptions;
        }

        public Set<User> getSubscribers() {
                return subscribers;
        }

        public void setSubscribers(Set<User> subscribers) {
                this.subscribers = subscribers;
        }

        public List<Chat> getChatMessagesSent() {
                return chatMessagesSent;
        }

        public void setChatMessagesSent(List<Chat> chatMessagesSent) {
                this.chatMessagesSent = chatMessagesSent;
        }

        public List<Chat> getChatMessagesReceived() {
                return chatMessagesReceived;
        }

        public void setChatMessagesReceived(List<Chat> chatMessagesReceived) {
                this.chatMessagesReceived = chatMessagesReceived;
        }

        /////

        public void addMovieToWishlist(Movie movie) {

                if (wishlist == null) {
                        wishlist = new HashSet<Movie>();
                }
                wishlist.add(movie);
        }

        public void addUserToSubscription(User user) {

                if (subscriptions == null) {
                        subscriptions = new HashSet<User>();
                }
                subscriptions.add(user);
        }

        public void addMessage(Chat message) {

                if (chatMessagesSent == null) {
                        chatMessagesSent = new ArrayList<Chat>();
                }
                chatMessagesSent.add(message);
        }

        public void addRateNotification(RateNotification rateNotification) {

                if (notifications== null) {
                        notifications = new ArrayList<Notification>();
                }
                notifications.add(rateNotification);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                User user = (User) o;
                return id.equals(user.id) &&
                        username.equals(user.username) &&
                        Objects.equals(email, user.email);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, username, email);
        }

        public List<Notification> getNotifications() {
                return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
                this.notifications = notifications;
        }
}
