package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static android.R.attr.x;
import static android.R.attr.y;
import static android.R.transition.move;
import static android.os.Build.VERSION_CODES.M;
import static com.dreamsofpines.mcunost.R.id.fill;
import static com.dreamsofpines.mcunost.R.id.indicator;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class InformExcursionFragment extends Fragment {

    public static final float SWIPE_MAX_OFF_PATH = 45;
    public static final float SWIPE_MIN_OFF_PATH = 4;
    private float downX,UpY=1;
    private float downY,moveX = 0,moveY = 0,diff=0;

    private MyDataBase mMyDataBase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform_excursion,container,false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.excure_view_pager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        final ImageView mImageView = (ImageView) view.findViewById(R.id.img_exc_inf);
        TextView title = (TextView) view.findViewById(R.id.title_exc_inf);
        TextView titlebar = (TextView) getActivity().findViewById(R.id.title_tour);
        titlebar.setText("Тур");

        indicator.configureIndicator(-1,-1,-1,
                R.animator.scale_with_alpha,0,R.drawable.circle_shape,R.drawable.circle_shape);

        final ExcurPagerAdapter adapter = new ExcurPagerAdapter(getActivity());
        mMyDataBase = new MyDataBase(getActivity());
        Bundle bundle = getArguments();
        List<String> list = mMyDataBase.getListExcursion(bundle.getString("pack_exc"));
        String listExcursion = createStringListExcursion(list);
        if(listExcursion.equalsIgnoreCase("")) {
            listExcursion = "Список экскурсий пуст";
        }

        Picasso.with(getActivity()).load("file:///android_asset/"+bundle.getString("img")+".png").into(mImageView);
        title.setText(bundle.getString("pack_exc"));
        adapter.setDay(bundle.getString("day"));
        adapter.setCost(bundle.getString("cost"));
        adapter.setFullText(bundle.getString("description"));
        adapter.setListExc(listExcursion);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
//        final RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.moveLayout);
//        final RelativeLayout rl3 = (RelativeLayout) view.findViewById(R.id.inf_about_exc);
//        final RelativeLayout rl2 = (RelativeLayout) getActivity().findViewById(R.id.titlebar);
//        final float rlH = rl.getHeight(),rl2H = rl2.getHeight(),rl2Y=rl2.getY();
//        float begi = rl.getY()+rlH;
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                float x = motionEvent.getX();
//                float y = motionEvent.getY();
//                switch(motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        downX = x;
//                        downY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        moveX = x;
//                        moveY = y;
//                        if(Math.abs(downX - moveX)>SWIPE_MAX_OFF_PATH) return false;
//                        float res =(moveY-downY);
//                        if(Math.abs(res)>SWIPE_MIN_OFF_PATH && res!=diff) {
//                            diff = res;
//                            float df = rl.getY();
//                            if(res<0) {
//                                if (df >= rl2H/2) {
//                                    rl.setY(df + res);
//                                }
//                                else
//                                    rl.setY(0);
//                            }else {
//                                float rlH1 = rl3.getHeight();
//                                float imH = mImageView.getY()+mImageView.getHeight();
//                                Log.i("Myapp",""+mImageView.getY()+" h" + mImageView.getHeight());
//                                if (df +rlH1/3 <= imH)
//                                    rl.setY(df + res);
//                                else {
//                                    rl.setY(imH);
//                                }
//                            }
//
//                            downY = moveY;
//                            return true;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        UpY = y;
//                        break;
//                }
//                return false;
//            }
//        });
        return view;
    }
    private String createStringListExcursion(List<String> list){
        String result="";
        for(int i = 0;i < list.size();++i ){
            result += (i+1)+". "+list.get(i)+"\n";
        }
        return result;
    }


}
