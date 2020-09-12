package com.solo.kinocavern.restcontroller;


import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.Chat;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/chats")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Set<User> loadChats(HttpServletRequest request){

        User currentUser = userService.loadCurrentUser(request);
        Set<User> userChats = new HashSet<>();
        List<Chat> messagesReceived = currentUser.getChatMessagesReceived();
        for (Chat chat: messagesReceived) {
            userChats.add(chat.getUserFrom());
        }
        List<Chat> messagesSent = currentUser.getChatMessagesSent();
        for(Chat chat: messagesSent){
            userChats.add(chat.getUserTo());
        }
        return userChats;
    }

    @GetMapping("/chats/{userToId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Chat> loadChatHistory(HttpServletRequest request,
                                           @PathVariable Long userToId){
        User currentUser = userService.loadCurrentUser(request);
        List<Chat> messagesReceived = currentUser.getChatMessagesReceived();
        messagesReceived.removeIf(element -> !(element.getUserFrom().getId().equals(userToId)));
        List<Chat> messagesSent = currentUser.getChatMessagesSent();
        messagesSent.removeIf(element -> !(element.getUserTo().getId().equals(userToId)));
        messagesReceived.addAll(messagesSent);
        return messagesReceived;
    }


    @PostMapping("/chats/{userToId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> postMessage(HttpServletRequest request,
                                           @PathVariable Long userToId,
                                           @RequestBody String message){

        if(message != null && message.length()>0){
            Map<String, String> response = userService.addChatMessage(request, userToId, message);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
