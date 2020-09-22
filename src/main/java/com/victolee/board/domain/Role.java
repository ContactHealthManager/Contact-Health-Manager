package com.victolee.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    TRAINER("ROLE_TRAINER"),
    HELLIN("ROLE_HELLIN");
    private String value;


}
