package com.yueping.testtsc4503;

import java.io.Serializable;

public class TscStsResult implements Serializable {
    private int code;
    private String message;
    private boolean isCanUse;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCanUse() {
        return isCanUse;
    }

    public void setCanUse(boolean canUse) {
        isCanUse = canUse;
    }
}
