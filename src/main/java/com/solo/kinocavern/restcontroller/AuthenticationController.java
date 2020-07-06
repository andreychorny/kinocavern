package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.RoleDAO;
import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.EnumRole;
import com.solo.kinocavern.entity.Role;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.payload.request.LoginRequest;
import com.solo.kinocavern.payload.request.SignupRequest;
import com.solo.kinocavern.payload.response.JwtResponse;
import com.solo.kinocavern.payload.response.MessageResponse;
import com.solo.kinocavern.security.service.UserDetailsImpl;
import com.solo.kinocavern.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        String roleName = roles.get(0);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roleName));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDAO.usernameExists(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userDAO.emailExists(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String stringRole = signUpRequest.getRole();
        Role role = null;
        switch (stringRole) {
            case "admin":
                role = roleDAO.findByName(EnumRole.ROLE_ADMIN);
                break;
            default:
                role  = roleDAO.findByName(EnumRole.ROLE_USER);
        }
        user.setRole(role);
        userDAO.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
