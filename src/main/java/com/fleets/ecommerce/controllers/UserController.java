package com.fleets.ecommerce.controllers;


import com.fleets.ecommerce.models.Users.UserDto;
import com.fleets.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService usersService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal UserDto user, @PathVariable Long id) {
        return ResponseEntity.ok().body(user);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<UserDto> admin(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok().body(user);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user")
    public ResponseEntity<UserDto> user(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok().body(user);
    }


}
