package com.edu.boardback.service;

import org.springframework.http.ResponseEntity;

import com.edu.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
