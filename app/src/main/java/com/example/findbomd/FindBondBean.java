package com.example.findbomd;

import android.widget.ImageView;

public class FindBondBean {

    private int text; // 记录自己旁有多少炸弹
    private boolean isFlag; // 旗帜

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
