package com.mycircle.NearMe;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by Carlos on 20/01/2016.
 */
public interface FragmentCallbacks {

    public void setToolbarHeight();

    public void setPanelState(SlidingUpPanelLayout.PanelState state, int markerID );
}
