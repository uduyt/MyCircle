package com.mycircle.NearMe;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.mycircle.Person;
import com.mycircle.PersonLab;
import com.mycircle.R;

import java.util.HashMap;


public class FragmentNearMeMaps extends Fragment implements OnMapReadyCallback {

    private static int updateDuration=5; //in seconds
    private static GoogleMap mMap;
    private static LocationManager lm;
    private static View rootView;
    private static FragmentCallbacks mFragmentCallbacks;
    private static double lat0, lat1, lat2, lon0, lon1, lon2;
    public static double radius;
    private static Location myLocation;
    private static LatLng myLatLng;
    private static HashMap<Marker, Integer> mHashMap;
    private static Circle mCircle;
    private static Context mContext;
    public FragmentNearMeMaps() {
        // Required empty public constructor
    }

    public static FragmentNearMeMaps newInstance() {
        FragmentNearMeMaps fragment = new FragmentNearMeMaps();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=getActivity();

        mHashMap = new HashMap<Marker, Integer>();
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        radius = 1000;

        myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat0 = myLocation.getLatitude();
        lon0 = myLocation.getLongitude();

        myLatLng = new LatLng(lat0, lon0);

        PersonLab.getPersonById(PersonLab.getMyId()).setLocation(myLatLng);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_near_me_maps, container, false);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);



        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mFragmentCallbacks= (FragmentCallbacks) context;
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize map
        if(myLocation!=null) {



            lat1 = lat0 + (180 / Math.PI) * (-radius / 6378137);
            lon1 = lon0 + (180 / Math.PI) * (+radius*1.5 / 6378137) / Math.cos(lat0);

            lat2 = lat0 + (180 / Math.PI) * (+radius / 6378137);
            lon2 = lon0 + (180 / Math.PI) * (-radius*1.5 / 6378137) / Math.cos(lat0);

            LatLngBounds meBounds = new LatLngBounds(new LatLng(lat1, lon1), new LatLng(lat2, lon2));

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(meBounds, 0));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (mHashMap.containsKey(marker)) {
                        int markerID;
                        markerID = mHashMap.get(marker);
                        //mFragmentCallbacks.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED, markerID);
                        marker.setSnippet(PersonLab.getPersonById(markerID).getAlias());
                    } else {
                        Toast.makeText(getActivity(), "the marker is not in the database", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
            });
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //mFragmentCallbacks.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN, 0);
                }
            });
            mCircle = mMap.addCircle(new CircleOptions()
                    .center(myLatLng)
                    .radius(radius)
                    .strokeColor(Color.parseColor("#553F51B5"))
            .fillColor(Color.parseColor("#552196F3")));
        }
        for(Person p: PersonLab.getPersons()){
            p.setMarker(mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(p.getMarkerIcon())).position(p.getLocation()).title(p.getAlias())));
        }
        final LocationListener locationListener = new LocationListener() {

            @Override
            public void onProviderEnabled(String string){

            }
            @Override
            public void onProviderDisabled(String string){

            }
            @Override
            public void onStatusChanged(String string, int pos, Bundle bundle){

            }

            public void onLocationChanged(Location location) {
                UpdateMe();
            }
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateDuration * 1000, 10, locationListener);

    }

    public static void DrawUpdatedContacts(){
        for(Person p: PersonLab.getPersons()){
            p.setMarker(mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(p.getMarkerIcon())).position(p.getLocation()).title(p.getAlias())));
        }
    }

    public static void UpdateMe(){
        //My Location
        myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lat0 = myLocation.getLatitude();
        lon0 = myLocation.getLongitude();

        myLatLng = new LatLng(lat0, lon0);

        PersonLab.getPersonById(PersonLab.getMyId()).setLocation(myLatLng);

        PersonLab.getPersonById(PersonLab.getMyId()).getMarker().setPosition(myLatLng);

        lat1 = lat0 + (180 / Math.PI) * (-radius / 6378137);
        lon1 = lon0 + (180 / Math.PI) * (+radius * 1.5 / 6378137) / Math.cos(lat0);

        lat2 = lat0 + (180 / Math.PI) * (+radius / 6378137);
        lon2 = lon0 + (180 / Math.PI) * (-radius * 1.5 / 6378137) / Math.cos(lat0);

        LatLngBounds meBounds = new LatLngBounds(new LatLng(lat1, lon1), new LatLng(lat2, lon2));


        mCircle.setCenter(myLatLng);
        mCircle.setRadius(radius);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(meBounds, 0));
    }
    public static void addMarkerToHashMap(Marker marker, int id){
        mHashMap.put(marker, id);
    }

    public static double getDistanceFromLatLon(double lat1,double lon1,double lat2,double lon2) {
        int R = 6371000;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }

}
