package com.alikaanbaci.quickstarter.controller;

import com.alikaanbaci.quickstarter.request.UserCreateRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    @ApiOperation(value = "New User adding method")
    public ResponseEntity<?> getUsers() {

        return ResponseEntity.ok().build();
    }
}
