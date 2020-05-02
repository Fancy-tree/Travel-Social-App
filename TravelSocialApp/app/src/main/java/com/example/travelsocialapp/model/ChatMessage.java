package com.example.travelsocialapp.model;


//用户发送的聊天内容
public class ChatMessage {

    private String mMassage;//聊天内容
    private int mType;//自己还是对方发的消息 0:自己 1:对方

    public ChatMessage(int type,String massage){
        this.mMassage = massage;
        this.mType = type;
    }

    public String getMassage() { return mMassage; }
    public void setMassage(String massage) { this.mMassage = massage; }
    public int getType() { return mType; }
    public void setType(int type) { this.mType = type; }


}
