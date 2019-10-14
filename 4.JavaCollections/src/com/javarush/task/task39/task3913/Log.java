package com.javarush.task.task39.task3913;

import java.util.Date;

public class Log {


    String ip;

    String user;

    Date date;

    Event event;

    Integer task;

    Status status;

    public Log(String ip, String user, Date date, Event event, Integer task, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        this.status = status;
        this.task = task;
    }
}
