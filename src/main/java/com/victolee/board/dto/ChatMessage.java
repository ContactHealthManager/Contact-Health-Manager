package com.victolee.board.dto;

import com.victolee.board.domain.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

}
