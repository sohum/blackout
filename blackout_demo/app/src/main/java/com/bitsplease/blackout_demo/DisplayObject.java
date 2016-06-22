package com.bitsplease.blackout_demo;

import java.io.Serializable;

/**
 * Created by sunny on 2016-06-18.
 */
<<<<<<< HEAD
public class DisplayObject {
    String type;
    String text;
    String time;
    int photoId;

    DisplayObject(String type, String time, String text, int photoId)
    {
        this.type = type;
        this.text = text;
        this.time = time;
        this.photoId = photoId;
=======
public class DisplayObject implements Serializable {
    private String text;
    private String type;
    private String time;

    public DisplayObject(String text, String type, String time){
        this.text = text;
        this.type = type;
        this.time = time;
    }

    public String getText(){
        return this.text;
    }
    public String getType(){
        return this.type;
    }
    public String getTime(){
        return this.time;
>>>>>>> master
    }

}
