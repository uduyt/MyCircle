package com.mycircle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 25/01/2016.
 */
public class CircleManager {
    private static List<Circle> circles= new ArrayList<Circle>();

    public static List<Circle> getCircles() {
        return circles;
    }

    public static void setCircles(List<Circle> circles) {
        CircleManager.circles = circles;
    }

    public static Circle getCircle(int pos) {
        return circles.get(pos);
    }

    public static Circle getCircle(String name) {

        for (Circle circle : circles){
            if (circle.getName().equals(name)) {
                return circle;
            }
        }
        return null;
    }

    public static Circle getCircleById(int circleId) {

        for (Circle circle : circles){
            if (circle.getCircleId()==circleId) {
                return circle;
            }
        }
        return null;
    }

    public static int addCircle(Circle circle, Context context){

        for (Circle mCircle : circles){
            if (mCircle.getName().equals(circle.getName())) {
                return -1;
            }
        }
        if(circle.getName().equals("")){
            return -2;
        }
        circles.add(circle);

        //TODO check in circle in the server to get circleId
        int circleId=CircleManager.getCircles().size();
        circle.setCircleId(circleId);

        myDataBaseHelper dbh= new myDataBaseHelper(context);
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.insert(myDataBaseHelper.CIRCLES_TABLE_NAME, null, circle.toContentValues());

        return 1;
    }
}
