package com.yueping.testtsc4503;

import java.io.Serializable;

public class TscPrinterStrListBean implements Serializable {

    private String strMsg;
    private String strMsgX;
    private String strMsgY;
    private String strMsgPosX;
    private String strMsgPosY;
    private boolean isNotChinese;

    public String getStrMsg() {
        return strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }

    public String getStrMsgX() {
        return strMsgX;
    }

    public void setStrMsgX(String strMsgX) {
        this.strMsgX = strMsgX;
    }

    public String getStrMsgY() {
        return strMsgY;
    }

    public void setStrMsgY(String strMsgY) {
        this.strMsgY = strMsgY;
    }

    public String getStrMsgPosX() {
        return strMsgPosX;
    }

    public void setStrMsgPosX(String strMsgPosX) {
        this.strMsgPosX = strMsgPosX;
    }

    public String getStrMsgPosY() {
        return strMsgPosY;
    }

    public void setStrMsgPosY(String strMsgPosY) {
        this.strMsgPosY = strMsgPosY;
    }

    public boolean isNotChinese() {
        return isNotChinese;
    }

    public void setNotChinese(boolean notChinese) {
        isNotChinese = notChinese;
    }
}
