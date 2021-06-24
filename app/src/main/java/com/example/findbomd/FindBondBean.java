package com.example.findbomd;

import android.widget.ImageView;

public class FindBondBean {

    private int text; // 记录自己旁有多少炸弹
    private boolean isBomb; // 炸弹
    private boolean isOpen; // 翻开
    private boolean isFlag; // 旗帜

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
