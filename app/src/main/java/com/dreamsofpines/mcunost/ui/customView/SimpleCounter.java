package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import static com.mikepenz.iconics.Iconics.init;

/**
 * Created by ThePupsick on 28.02.2018.
 */

public class SimpleCounter extends RelativeLayout {

    public SimpleCounter(Context context) {
        super(context);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
           //do something
    }


}
