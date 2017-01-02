package com.mycircle;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.mycircle.NearMe.FragmentNearMeMaps;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private Bitmap mIcon;
    private Bitmap markerIcon;
    private String mName;
    private LatLng mLocation;
    private History mHistory;
    private Context mContext;
    private String mAlias;
    private int personID;
    private Marker mMarker;
    private List<Circle> Circles = new ArrayList<Circle>();


    public Bitmap getMarkerIcon() {
        return markerIcon;
    }

    public void setMarkerIcon(Bitmap markerIcon) {
        this.markerIcon = markerIcon;
    }

    public String getAlias() {
        return mAlias;
    }

    public void setAlias(String mAlias) {
        this.mAlias = mAlias;
    }


    public int getPersonID() {

        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setMarker(Marker mMarker) {
        this.mMarker = mMarker;
        FragmentNearMeMaps.addMarkerToHashMap(mMarker,getPersonID());
    }

    public Marker getMarker() {
        return mMarker;
    }

    public Person(Context c){
        mContext = c;
        mIcon = BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
        markerIcon = BitmapFactory.decodeResource(c.getResources(), R.mipmap.marker_custom);
    }
    public Person(String name, Context c) {
        mContext = c;
        mName = name;
        mIcon = BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
        markerIcon = BitmapFactory.decodeResource(c.getResources(), R.mipmap.marker_custom);
    }

    public void setIcon(Bitmap icon) {
        mIcon = icon;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setLocation(LatLng location) {
        mLocation = location;
    }

    public void fillPerson(JSONObject json) {

    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public String getName() {
        return mName;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public ContentValues toContentValues(){
        android.content.ContentValues mContentValues = new ContentValues();

        mContentValues.put(myDataBaseHelper.CONTACTS_PERSON_ID,     this.personID);
        mContentValues.put(myDataBaseHelper.CONTACTS_NAME,          this.mName);
        mContentValues.put(myDataBaseHelper.CONTACTS_ALIAS,         this.mAlias);
        mContentValues.put(myDataBaseHelper.CONTACTS_LATITUDE,      this.getLocation().latitude);
        mContentValues.put(myDataBaseHelper.CONTACTS_LONGITUDE,     this.getLocation().longitude);
        return mContentValues;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private Person mPerson;
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvDistance;
        private int mDistance;

        public PersonViewHolder(View itemView) {
            super(itemView);

            ivIcon = (ImageView) itemView.findViewById(R.id.iv_list_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_list_name);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_list_distance);
            mDistance = 50;
        }


        public void bindPerson(Person person) {
            mPerson = person;
            ivIcon.setImageBitmap(person.getIcon());
            tvName.setText(person.getName());
            mDistance=(int) Math.abs(Math.round(FragmentNearMeMaps.getDistanceFromLatLon(PersonLab.getMe().getLocation().latitude
                    ,PersonLab.getMe().getLocation().longitude,PersonLab.getPersonById(person.getPersonID()).getLocation().latitude
                    ,PersonLab.getPersonById(person.getPersonID()).getLocation().longitude)));
            tvDistance.setText(String.valueOf(mDistance) + "m");
        }
    }

    public static class PersonAdapter
            extends RecyclerView.Adapter<PersonViewHolder> {

        private List<Person> mPersons;

        public PersonAdapter(List<Person> persons) {

            mPersons = persons;
            //mPersons.remove(PersonLab.getMe());
        }

        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_list_item, parent, false);
            return new PersonViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonViewHolder holder, int pos) {

            Person person = mPersons.get(pos+1);
            holder.bindPerson(person);
        }

        @Override
        public int getItemCount() {
            return mPersons.size()-1;
        }
    }
}
