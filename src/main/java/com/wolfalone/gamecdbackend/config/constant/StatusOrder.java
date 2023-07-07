package com.wolfalone.gamecdbackend.config.constant;

public enum StatusOrder {
    PENDING(0),
    SHIPPING(1)
    ;
    private final int status;

    StatusOrder(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    };
}
