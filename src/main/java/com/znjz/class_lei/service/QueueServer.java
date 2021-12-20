package com.znjz.class_lei.service;

public interface QueueServer {

    public void sendMessage(String message,String rk);

    public void sendCommonMessage(String message);




}
