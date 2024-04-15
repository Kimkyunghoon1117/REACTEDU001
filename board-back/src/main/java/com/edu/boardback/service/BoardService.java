package com.edu.boardback.service;

import org.springframework.http.ResponseEntity;

import com.edu.boardback.dto.request.board.PostBoardRequestDto;
import com.edu.boardback.dto.response.borad.PostBoardResponseDto;

public interface BoardService {
    ResponseEntity<? super PostBoardResponseDto> postBorad(PostBoardRequestDto dto, String email);  
}