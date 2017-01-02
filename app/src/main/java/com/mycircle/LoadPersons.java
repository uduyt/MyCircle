package com.mycircle;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class LoadPersons extends AsyncTask<Object, Object, List<Person>> {
    private Context mContext;

    @Override
    protected List<Person> doInBackground(Object... params) {
        mContext=(Context) params[0];
        List<Person> Persons = new ArrayList<Person>();

        Person person = new Person(mContext);
        person.setName("Carlos Rosety");
        person.setAlias("rosety");
        person.setPersonID(1);
        person.setMarkerIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.marker_custom_me));
        Persons.add(person);

        person = new Person(mContext);
        person.setName("Luis Diaz");
        person.setAlias("Luiis");
        person.setLocation(new LatLng(40.436078, -3.731995));
        person.setPersonID(2);
        Persons.add(person);

        person = new Person(mContext);
        person.setName("Edu Brage");
        person.setAlias("Eedu");
        person.setLocation(new LatLng(40.435779, -3.733805));
        person.setPersonID(3);
        Persons.add(person);

        person = new Person(mContext);
        person.setName("Mauricio Muñoz");
        person.setAlias("Mauri");
        person.setLocation(new LatLng(40.430239, -3.713924));
        person.setPersonID(4);
        Persons.add(person);

        //TODO: Request persons from server and add them to the Persons list

        return Persons;
    }

    @Override
    protected void onPostExecute(List<Person> Persons) {
        super.onPostExecute(Persons);



        PersonLab.setPersons(Persons);
    }

}
