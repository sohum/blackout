package com.bitsplease.blackout_demo;

import java.io.Serializable;

/**
 * Created by sunny on 2016-06-18.
 */
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
    }

}
