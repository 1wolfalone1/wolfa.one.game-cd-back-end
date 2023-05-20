package com.wolfalone.gamecdbackend.config.constant;

public enum AccountStatus {
    ENABLE(1),
    DISABLE(-1),
    PENDING(0)
    ;


    private final int status;

    AccountStatus(int status) {
        this.status = status;
    }
}
