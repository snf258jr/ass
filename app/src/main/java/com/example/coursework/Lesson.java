package com.example.coursework;

import java.util.Date;

public class Lesson {
    private int id = 0;
    private String name;
    private String description;
    private String date;
    private String timeslot;
    private int quota;
    private String man;
    private Date createTime;
    private Date modifyTime;

    public Lesson() {

    }

    public Lesson(String name, String description, String date, String timeslot, int quota, String man) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeslot = timeslot;
        this.quota = quota;
        this.man = man;
    }

    public Lesson(String name, String description, String date, String timeslot, int quota, String man, Date createTime) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeslot = timeslot;
        this.quota = quota;
        this.man = man;
        this.createTime = new Date();

    }

    public Lesson(String name, String description, String date, String timeslot, int quota, String man, Date createTime, Date modifyTime) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeslot = timeslot;
        this.quota = quota;
        this.man = man;
        this.createTime = new Date();
        this.modifyTime = new Date();
    }


    public void setModifyTime(Date modifyTime) { this.modifyTime = modifyTime; }
    public Date getModifyTime() { return modifyTime; }

    public Date getCreateTime() { return createTime; }
    //Setter should not required since the createTime should not be modified
    //private void setCreateTime(Date createTime) { this.createTime = createTime; }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

}

