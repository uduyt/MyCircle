package com.mycircle.NearMe;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.mycircle.PersonLab;
import com.mycircle.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class FragmentNearMe extends Fragment {

    private static View mRootView;
    private View mFilterDrawer;
    private Spinner spinner;
    private SeekBar seekbar;
    private static TextView tvProfileName;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private SlidingUpPanelLayout mLayout;
    private View flSlidingPanel;
    private EditText etSeekBar;

    public FragmentNearMe() {
    }

    public static FragmentNearMe newInstance(String param1, String param2) {
        FragmentNearMe fragment = new FragmentNearMe();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_near_me, container, false);

        //Navigation drawer
        DrawerLayout drawer = (DrawerLayout) mRootView.findViewById(R.id.drawer_layout);
        mFilterDrawer = mRootView.findViewById(R.id.filter_drawer);

        //spinner

        mFilterDrawer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                spinner = (Spinner) mRootView.findViewById(R.id.spinner_order_by);

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.array_order_by, R.layout.spinner_item_custom_drawer);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                etSeekBar = (EditText) mRootView.findViewById(R.id.et_seekbar);
                etSeekBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() != 0) {

                            if (Integer.parseInt(s.toString()) < 50) {
                                seekbar.setProgress(50);
                                FragmentNearMeMaps.radius = 50;
                                FragmentNearMeMaps.UpdateMe();
                            } else if (Integer.parseInt(s.toString()) > 5000) {
                                seekbar.setProgress(5000);
                                etSeekBar.setText("5000");

                                FragmentNearMeMaps.radius = 5000;
                                FragmentNearMeMaps.UpdateMe();
                            } else {
                                seekbar.setProgress(Integer.parseInt(s.toString()));
                                FragmentNearMeMaps.radius = Integer.parseInt(s.toString());
                                FragmentNearMeMaps.UpdateMe();
                            }

                        }
                    }
                });
                //SeekBar
                seekbar = (SeekBar) mRootView.findViewById(R.id.seekbar_distance);
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (!etSeekBar.hasFocus()) {
                            etSeekBar.setText(String.valueOf(progress));
                        }


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                seekbar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                // Disallow Drawer to intercept touch events.
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                etSeekBar.clearFocus();
                                break;

                            case MotionEvent.ACTION_UP:
                                // Allow Drawer to intercept touch events.
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }

                        // Handle seekbar touch events.
                        v.onTouchEvent(event);
                        return true;
                    }
                });
            }
        });

        //ViewPager
        mSectionsPagerAdapter = new FragmentNearMe.SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_near_me);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //mLayout.setPanelHeight(Math.round(70 * getResources().getDisplayMetrics().density*(1-positionOffset)));
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    //mLayout.setEnabled(false);
                    //mLayout.setPanelHeight(Math.round(0));
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                } else {
                    //mLayout.setEnabled(true);
                    //mLayout.setPanelHeight(Math.round(70 * getResources().getDisplayMetrics().density));
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });

        final TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        mLayout = (SlidingUpPanelLayout) mRootView.findViewById(R.id.sliding_layout);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                /*TextView tv = (TextView) findViewById(R.id.tv_profile_name);
                LinearLayout ll = (LinearLayout) findViewById(R.id.ll_profile_top);

                int b = Math.round(227 * slideOffset + 28);
                int g = b;
                int r = Math.round(72 * slideOffset + 183);

                //tv.setText(String.valueOf(slideOffset));
                tv.setTextColor(Color.rgb(r, b, g));

                b = Math.round(227 * (1 - slideOffset) + 28);
                g = b;
                r = Math.round(72 * (1 - slideOffset) + 183);

                ll.setBackgroundColor(Color.rgb(r, b, g));*/
            }

            @Override
            public void onPanelCollapsed(View panel) {

            }

            @Override
            public void onPanelExpanded(View panel) {
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
        tvProfileName = (TextView) mRootView.findViewById(R.id.tv_profile_name);


        return mRootView;
    }

    //Filters Drawer Toggle Action bar button
    public void FiltersToggle(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) mRootView.findViewById(R.id.drawer_layout);
        if (!drawer.isDrawerOpen(mFilterDrawer)) {
            drawer.openDrawer(mFilterDrawer);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) {
                return FragmentNearMeMaps.newInstance();
            } else {
                return FragmentNearMeList.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mapa";
                case 1:
                    return "Lista";
            }
            return null;
        }
    }

    //TODO
    /*@Override
    public void setToolbarHeight(){

        *//*RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mToolbar.getLayoutParams();
        layoutParams.height = height;
        mToolbar.setLayoutParams(layoutParams);*//*
    }

    @Override
    public void setPanelState(SlidingUpPanelLayout.PanelState state, int markerID){
        mLayout.setPanelState(state);
        if(markerID!=0) {
            tvProfileName.setText(PersonLab.getPersonById(markerID).getName());
        }
    }*/
}


