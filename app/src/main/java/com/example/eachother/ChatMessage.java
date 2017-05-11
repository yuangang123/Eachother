package com.example.eachother;

/**
 * Created by 袁刚 on 2017/5/5.
 */

public class ChatMessage {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SEND=1;
    private String content;
    private int type;

    public ChatMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
