package com.solo.kinocavern.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
        @Size(min = 5, max = 20)
        @Column(name="username")
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        @Column(name="email")
        private String email;

        @NotBlank
        @Size(min = 6 ,max = 60)
        @Column(name="password")
        private String password;

        @ManyToOne
        @JoinColumn(name = "role_id",referencedColumnName="id")
        private Role role;

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
}
