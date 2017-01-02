package com.mycircle;

import android.content.ContentValues;

/**
 * Created by Carlos on 25/01/2016.
 */
public class Circle {

    private String name;
    private int[] personids;
    private int circleId;
    private String circleStatus="private";
    private int persons;
    private int creatorID;

    public int getPersons() {
        return persons;
    }

    public String getName() {
        return name;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public void setCircleId(int circleId) {

        this.circleId = circleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getPersonids() {
        return personids;
    }

    public void setPersonids(int[] personids) {
        this.personids = personids;
    }

    public int getCircleId() {
        return circleId;
    }

    public String getCircleStatus() {
        return circleStatus;
    }

    public void setCircleStatus(String circleStatus) {
        this.circleStatus = circleStatus;
    }

    public ContentValues toContentValues(){
        ContentValues mContentValues = new ContentValues();

        mContentValues.put(myDataBaseHelper.CIRCLES_CIRCLE_NAME,    this.name);
        mContentValues.put(myDataBaseHelper.CIRCLES_CIRCLE_STATE,   this.circleStatus);
        mContentValues.put(myDataBaseHelper.CIRCLES_PERSONS,        this.personids.length);
        mContentValues.put(myDataBaseHelper.CIRCLES_CREATOR_ID,     this.creatorID);
        return mContentValues;
    }
}
