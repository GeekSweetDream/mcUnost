package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.ui.adapters.pager.ExcurPagerAdapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static android.R.id.list;

/**
 * Created by ThePupsick on 14.08.17.
 */

public class TextInformFragment extends Fragment {

    private MyDataBase mMyDataBase;
    private Button mButton;
    private ViewPager viewPager;
    private CircleIndicator indicator;
    private ExcurPagerAdapter adapter;
    private Button butt;
    public static OnClickButtonBookTour mListener;

    public interface OnClickButtonBookTour{
        void onClicked();
    }

    public void setOnClickButtonListenner(OnClickButtonBookTour listener){
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_inform_text,container,false);
        bindAcitivty(view);

        indicator.configureIndicator(-1,-1,-1,
                R.animator.scale_with_alpha,0,R.drawable.circle_shape,R.drawable.circle_shape);

        Bundle bundle = getArguments();
        adapter = new ExcurPagerAdapter(getActivity());
        adapter.setDay(bundle.getString("day"));
        adapter.setShDesc(bundle.getString("sh_desc"));
        adapter.setFullText(bundle.getString("description"));
        adapter.setAddService(addDot(bundle.getString("addService"),true));
        adapter.setInTour(addDot(bundle.getString("inTour"),false));
        adapter.setOnClickListenner(new ExcurPagerAdapter.OnClickButton() {
            @Override
            public void onClicked() {
                mListener.onClicked();
            }
        });
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        return view;
    }

    private void bindAcitivty(View view){
        viewPager = (ViewPager) view.findViewById(R.id.excure_view_pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
    }

    private String addDot(String list, boolean isService){
        String result="";
        String[] str = list.split("\n");

//        char ch = isService? '✘':'✓';           //  ✘ и ✔ - не нашел их код, если найдете, будет неплохо
        for(int i =0 ; i < str.length; ++i){
            result += str[i]+"\n";
        }
        return result;
    }

}
