package com.wolfalone.gamecdbackend.config.constant;

import lombok.Data;


public enum UserRole {
    GUESS(-1),
    USER(0),
    ADMIN(1)
    ;
    UserRole(int role){
        this.role = role;
    }
    private int role;
    int getRole() {
        return this.role;
    }
}
