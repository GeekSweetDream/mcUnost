package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class InformExcursionFragment extends Fragment {

    private TextInformFragment mTextInformFragment;
    private CalculatorInformFragment mCalculatorInformFragment;
    private Bundle bundle, mBundle;
    private TextView cost, day;
    private View viewReg, viewSuccses, viewOrder;
    private Button mAutoInt, mRegistr, mCancel;
    private boolean showView;
    private Order mOrder;

    public static OnBookTourListener mBookListener;
    public interface OnBookTourListener{
        void onBooked();
    }
    public void setmBookListener(OnBookTourListener listener){mBookListener = listener;}

    public static OnClickRegListener mListener;

    public interface  OnClickRegListener{
        void onClickedRegButt();
    }

    public void setOnClickRegListener(OnClickRegListener listener){
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform_excursion,container,false);
        bundle = getArguments();
        mBundle = new Bundle();
        mBundle.putString("day",bundle.getString("day"));
        mBundle.putString("pack_exc",bundle.getString("pack_exc"));
        final ImageView mImageView = (ImageView) view.findViewById(R.id.img_exc_inf);
        TextView title = (TextView) view.findViewById(R.id.title_exc_inf);
        TextView titlebar = (TextView) getActivity().findViewById(R.id.title_tour);
        titlebar.setText("Тур");
        cost = (TextView) view.findViewById(R.id.cost_tour);
        day = (TextView) view.findViewById(R.id.we);
        cost.setText(bundle.getString("cost"));
        day.setText(bundle.getString("day")+" дней");
        Picasso.with(getActivity()).load("file:///android_asset/"+bundle.getString("img")+".png").into(mImageView);
        title.setText(bundle.getString("pack_exc"));
        viewSuccses = (View) view.findViewById(R.id.view_succsesful);
        viewOrder = (View) getActivity().findViewById(R.id.order_notification);
        viewReg = (View) view.findViewById(R.id.view_registr);
        viewReg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        mCancel = (Button) viewReg.findViewById(R.id.butt_registr_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewReg.setVisibility(View.INVISIBLE);
            }
        });
        mAutoInt = (Button) viewReg.findViewById(R.id.butt_autoriz);
        mAutoInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Данный функционал еще не добавлен! :)",Toast.LENGTH_SHORT)
                        .show();
                viewReg.setVisibility(View.INVISIBLE);
            }
        });
        mRegistr = (Button) viewReg.findViewById(R.id.butt_registr);
        mRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickedRegButt();
                viewReg.setVisibility(View.INVISIBLE);
            }
        });
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final FragmentManager mFragmentManager = getChildFragmentManager();
        if(null == mTextInformFragment){
            mTextInformFragment = new TextInformFragment();
            mTextInformFragment.setOnClickButtonListenner(new TextInformFragment.OnClickButtonBookTour() {
                @Override
                public void onClicked() {
                    if(null == mCalculatorInformFragment) {
                        mCalculatorInformFragment = new CalculatorInformFragment();
                    }
                    mCalculatorInformFragment.setArguments(mBundle);
                    mCalculatorInformFragment.setClickOkListenner(new CalculatorInformFragment.OnClickOk() {
                        @Override
                        public void onClicked(boolean isLogin, Order order) {
                            mOrder = order;
                            if(isLogin){
                                viewOrder = (View) getActivity().findViewById(R.id.order_notification);
                                viewOrder.setVisibility(View.VISIBLE);
                                insertOrderInDb(mOrder);
                                mBookListener.onBooked();
//                                try{
//                                    File file = new File(getContext().getFilesDir(),"order");
//                                    FileWriter fw = new FileWriter(getContext().getFilesDir()+"order");
//                                    BufferedWriter bw = new BufferedWriter(fw);
//                                    bw.write(getOrder(data,countPupil,countTeacher).toString());
//                                    bw.close();
//                                }catch (IOException e){
//                                    Toast.makeText(getContext(),"Ошибка чтения",Toast.LENGTH_SHORT).show();
//                                }
                                showSuccsesView();
                            }else{
                                viewReg.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    mCalculatorInformFragment.setClickCancelListenner(new CalculatorInformFragment.OnClickCancel() {
                        @Override
                        public void onClicked() {
                            mFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout_tour,mTextInformFragment)
                                    .commit();
                        }
                    });
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_tour,mCalculatorInformFragment)
                            .commit();
                }
            });
            mTextInformFragment.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .add(R.id.frame_layout_tour,mTextInformFragment)
                    .commit();
        }
    }

    private JSONObject getOrder(String data, String countPupil, String countTeacher){
        JSONObject js=null;
        try {
            js = new JSONObject();
            js.put("tour", bundle.getString("pack_exc"));
            js.put("data", data);
            js.put("pupil", countPupil);
            js.put("teacher",countTeacher);
        }catch(JSONException e){}

        return js;
    }

    private void insertOrderInDb(Order order){
        MyDataBase db = new MyDataBase(getActivity());
        db.setNewOrderUser(order.getDate(),order.getCost(),order.getTeachers(),order.getPupils(),order.getTour());
        db.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(showView){
            viewOrder.setVisibility(View.VISIBLE);
            insertOrderInDb(mOrder);
            showSuccsesView();
            showView = false;
        }

    }


    public void setShowView(boolean showView) {
        this.showView = showView;
    }

    public void showSuccsesView(){
        viewSuccses.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewSuccses.setVisibility(View.INVISIBLE);
            }
        },3000);
    }

}

