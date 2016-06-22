package com.bitsplease.blackout_demo;
import java.io.Serializable;
/**
 * Created by sunny on 2016-06-18.
 */
public class DisplayObject implements Serializable {
    String type;
    String text;
    String time;
    int photoId;

    DisplayObject(String type, String time, String text, int photoId) {
        this.type = type;
        this.text = text;
        this.time = time;
        this.photoId = photoId;
    }

    public String getText(){
        return this.text;
    }
    public String getType(){
        return this.type;
    }
    public String getTime(){
        return this.time;
    }
    public int getPhotoId(){
        return this.photoId;
    }

}
