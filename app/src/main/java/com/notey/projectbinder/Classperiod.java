package com.notey.projectbinder;

public class Classperiod {
    private String name = "null";
    private String startTime = "null";
    private String endTime = "null";
    private String weekdays = "null";
    private String subject = "null";

    public Classperiod(String name, String startTime, String endTime, String weekdays, String subject){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekdays = weekdays;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
