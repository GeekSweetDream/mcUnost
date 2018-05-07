package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.dialog.AuthDialogFragment;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dreamsofpines.mcunost.R.anim.jump_item;
import static com.dreamsofpines.mcunost.R.anim.slide_left_2_obj;
import static com.dreamsofpines.mcunost.R.anim.slide_right_2_obj;
//import static com.dreamsofpines.mcunost.R.id.book_now;
//import static com.dreamsofpines.mcunost.R.id.titlebar;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class InformExcursionFragment extends Fragment implements AuthDialogFragment.OnClickedReg, AuthDialogFragment.OnSuccessAuth {

    private TextInformFragment mTextInformFragment;
    private CalculatorInformFragment mCalculatorInformFragment;
    private Bundle bundle, mBundle;
    private TextView cost, day,logoR, year, title, titlebar;
    private RelativeLayout name;
    private View viewSuccses, viewOrder;
    private ImageView logoC,logoY, canv, mImageView, infoPattern, showOrderButton;;
    private boolean showView;
    private Order ord;
    private RelativeLayout rlSuccses;
    private Button ordButton;
    private boolean flCalculate = false, isShowed = false;
    private AVLoadingIndicatorView load;

    public static OnClickRegListener mListener;
    public static OnBookTourListener mBookListener;
    public OnSuccessAuth mSuccessAuth;

    public interface OnBookTourListener{
        void onBooked();
    }

    public void setmBookListener(OnBookTourListener listener){ mBookListener = listener;}

    public interface  OnClickRegListener{
        void onClickedRegButt();
    }

    public void setOnClickRegListener(OnClickRegListener listener){
        mListener = listener;
    }


    public interface OnSuccessAuth{
        void onSuccess();
    }

    public void setOnSuccessAuthListener(OnSuccessAuth listener) { mSuccessAuth = listener;}

    @Override
    public void onSuccess() {
        mSuccessAuth.onSuccess();
        viewSuccses.setVisibility(View.VISIBLE);
        load.show();
        AddNewOrderTask newOrderTask = new AddNewOrderTask();
        newOrderTask.execute(ord);
    }

    @Override
    public void onClickedReg() {
        mListener.onClickedRegButt();
    }

    @Override
    public void onDestroyView() {
        infoPattern.setVisibility(View.GONE);
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform_excursion,container,false);
        bundle = getArguments();
        mBundle = new Bundle();
        mBundle.putString("idPack",bundle.getString("idPack"));
        mBundle.putString("day",bundle.getString("day"));
        mBundle.putString("pack_exc",bundle.getString("pack_exc"));

        bindView(view);

        titlebar.setText("Тур");
        cost.setText(bundle.getString("cost"));
        day.setText(getDayString(bundle.getString("day")));
        title.setText(bundle.getString("pack_exc"));

        infoPattern.setVisibility(View.VISIBLE);
        infoPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Данный тур представлен как шаблон, вы можете изменить в нем все по вашему усмотрению!", Toast.LENGTH_LONG)
                        .show();
            }
        });

        showOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isShowed) {
                    final Animation slideL = AnimationUtils.loadAnimation(getActivity(), slide_left_2_obj);
                    canv.setAnimation(slideL);
                    canv.setVisibility(View.VISIBLE);
                    ordButton.setAnimation(slideL);
                    ordButton.setVisibility(View.VISIBLE);
                    showOrderButton.setBackgroundResource(R.mipmap.but_calc_hide);
                    isShowed = true;
                }else{
                    final Animation slideL = AnimationUtils.loadAnimation(getActivity(), slide_right_2_obj);
                    canv.setAnimation(slideL);
                    ordButton.setAnimation(slideL);
                    ordButton.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            showOrderButton.setBackgroundResource(R.mipmap.but_calc);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    canv.setVisibility(View.GONE);
                    ordButton.setVisibility(View.GONE);

                    isShowed = false;
                }
            }
        });

        Picasso.with(getActivity()).load(Constans.URL.DOWNLOAD.GET_IMG+bundle.getString("img")).into(mImageView);
        setAnimationForView();
        return view;
    }

    private void setAnimationForView(){
        Animation jumpDown = AnimationUtils.loadAnimation(getActivity(), jump_item);
        Animation jumpUp = AnimationUtils.loadAnimation(getActivity(),R.anim.jump_up_item);

        name.setAnimation(jumpUp);
        logoR.setAnimation(jumpDown);
        year.setAnimation(jumpDown);
        logoC.setAnimation(jumpDown);
        logoY.setAnimation(jumpDown);
        day.setAnimation(jumpDown);
        cost.setAnimation(jumpDown);
    }

    private void bindView(View view){
        mImageView = (ImageView) view.findViewById(R.id.img_exc_inf);
        title = (TextView) view.findViewById(R.id.title_exc_inf);
        titlebar = (TextView) getActivity().findViewById(R.id.title_tour);
        logoR = (TextView) view.findViewById(R.id.logo_rubles);
        year = (TextView) view.findViewById(R.id.text_years);
        logoC = (ImageView) view.findViewById(R.id.logo_calendar);
        logoY = (ImageView) view.findViewById(R.id.logo_years);
        name = (RelativeLayout) view.findViewById(R.id.title_name_tour);
        cost = (TextView) view.findViewById(R.id.cost_tour);
        day = (TextView) view.findViewById(R.id.we);
        viewSuccses = (View) view.findViewById(R.id.view_succsesful);
        rlSuccses = (RelativeLayout) viewSuccses.findViewById(R.id.rl_anim_succsesful);
        infoPattern = (ImageView) getActivity().findViewById(R.id.info_pattern);
        showOrderButton = (ImageView) view.findViewById(R.id.show_order_button);
        ordButton = (Button) view.findViewById(R.id.butt_order_tour);
        canv = (ImageView) view.findViewById(R.id.pouring_canvas);
        load = (AVLoadingIndicatorView) viewSuccses.findViewById(R.id.success_indicator);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final FragmentManager mFragmentManager = getChildFragmentManager();
        if(null == mTextInformFragment){
            mTextInformFragment = new TextInformFragment();
        }
        if(null == mCalculatorInformFragment) {
            mCalculatorInformFragment = new CalculatorInformFragment();
        }

        ordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    flCalculate = true;
                    ordButton.setVisibility(View.GONE);
                    showOrderButton.setVisibility(View.GONE);
                    mCalculatorInformFragment.setArguments(mBundle);
                    mCalculatorInformFragment.setClickOkListenner(new CalculatorInformFragment.OnClickOk() {
                        @Override
                        public void onClicked(boolean isLogin, Order order) {
                            AddNewOrderTask newOrderTask = new AddNewOrderTask();
                            ord = order;
                            if(isLogin){
                                viewSuccses.setVisibility(View.VISIBLE);
                                load.show();
                                newOrderTask.execute(order);
                            }else{
                                AuthDialogFragment aD = AuthDialogFragment.newInstace(InformExcursionFragment.this,
                                        InformExcursionFragment.this);
                                aD.show(getFragmentManager(),"AuthDialog");
                            }
                        }
                    });

                    final Animation showUp = AnimationUtils.loadAnimation(getContext(),R.anim.show_up);
                    final Animation showDown = AnimationUtils.loadAnimation(getContext(), R.anim.show_down);
                    canv.setAnimation(showUp);
                    canv.setVisibility(View.VISIBLE);
                    final Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout_tour,mCalculatorInformFragment)
                                    .commit();
                            canv.setVisibility(View.INVISIBLE);
                        }
                    },400);
                    mCalculatorInformFragment.setClickCancelListenner(new CalculatorInformFragment.OnClickCancel() {
                            @Override
                            public void onClicked() {
                                canv.setAnimation(showDown);
                                canv.setVisibility(View.VISIBLE);
                                mFragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout_tour, mTextInformFragment)
                                        .commit();
                                //canv.setVisibility(View.INVISIBLE);
                                ordButton.setVisibility(View.VISIBLE);
                                showOrderButton.setVisibility(View.VISIBLE);
                        }
                    });

                }
        });

        if(!flCalculate) {
            mTextInformFragment.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .add(R.id.frame_layout_tour, mTextInformFragment)
                    .commit();
            showOrderButton.setVisibility(View.VISIBLE);
        }

    }

    public String getDayString(String str){
        StringBuilder day = new StringBuilder(str);
        if(Integer.parseInt(str)==1){
            day.append(" день");
        }else if(Integer.parseInt(str)<5){
            day.append(" дня");
        }else
            day.append(" дней");
        return day.toString();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(showView){
            viewSuccses.setVisibility(View.VISIBLE);
            load.show();
            AddNewOrderTask newOrderTask = new AddNewOrderTask();
            newOrderTask.execute(ord);
            showView = false;
        }
    }


    public void setShowView(boolean showView) {
        this.showView = showView;
    }

    public void showSuccsesView(){
        load.hide();
        rlSuccses.setVisibility(View.VISIBLE);
        final Animation showUp = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down);
        rlSuccses.setAnimation(showUp);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewSuccses.setVisibility(View.INVISIBLE);
            }
        },3000);
        mBookListener.onBooked();
    }

    private class AddNewOrderTask extends AsyncTask<Order,Void,Boolean>{

        private String errorMsg =  "Ooops! Проблемы сети, попробуйте позже! =)";

        @Override
        protected Boolean doInBackground(Order... orders) {
            boolean success=true;
            JSONObject jsOrder = new JSONObject();
            Order ord = orders[0];
            try {
                jsOrder.put("nameTour", ord.getTour());
                jsOrder.put("cost", ord.getCost());
                jsOrder.put("quantityChildren", ord.getPupils());
                jsOrder.put("quantityTeacher", ord.getTeachers());
                jsOrder.put("dateTravelTour",ord.getDate());
                jsOrder.put("idCustomer", GlobalPreferences.getPrefIdUser(getContext()));
                jsOrder.put("idStatus", 1);
                String answer = RequestSender.POST(getContext(),Constans.URL.ORDER.ADD_NEW_ORDER,jsOrder,true);
                JSONObject js = new JSONObject(answer);
                if(!js.getString("result").equalsIgnoreCase("success")){
                    success = false;
                }
            }catch (JSONException e){
                Log.i("Myapp","Create JSON Order! Error message: " + e.getMessage());
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result){
                showSuccsesView();
            }else {
                Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }


}

