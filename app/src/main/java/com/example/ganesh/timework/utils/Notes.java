package com.example.ganesh.timework.utils;

/**
 * Created by Ganesh Prasad on 28-07-2016.
 */
public class Notes {

    private String name;
    private String content;
    private String type;
    private int createdHour;
    private int createdMinutes;
    private int createdDate;
    private int createdMonth;

    public Notes( String _name , String _type , String _content ) {

        this.name = _name;
        this.type = _type;
        this.content = _content;

    }

    public void setCreatedHour(int createdHour) {
        this.createdHour = createdHour;
    }

    public void setCreatedMinutes(int createdMinutes) {
        this.createdMinutes = createdMinutes;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedMonth(int createdMonth) {
        this.createdMonth = createdMonth;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getCreatedHour() {
        return createdHour;
    }

    public int getCreatedMinutes() {
        return createdMinutes;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public int getCreatedMonth() {
        return createdMonth;
    }
}
