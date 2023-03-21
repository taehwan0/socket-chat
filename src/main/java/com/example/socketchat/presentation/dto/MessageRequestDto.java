package com.example.socketchat.presentation.dto;

import lombok.Getter;

@Getter
public class MessageRequestDto {
    private String content;
    private String roomId;
}
