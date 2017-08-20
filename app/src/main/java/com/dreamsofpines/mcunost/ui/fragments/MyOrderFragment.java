package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;
import com.dreamsofpines.mcunost.ui.adapters.recyclerOrder.OrderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by ThePupsick on 19.08.17.
 */

public class MyOrderFragment extends Fragment {

    private FragmentManager fm;
    private InfoOrder iO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order,container,false);
        TextView textView = (TextView) view.findViewById(R.id.title_my_order);
        RecyclerView rec = (RecyclerView) view.findViewById(R.id.recycler_my_order);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Заявки");

        rec.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyDataBase db = new MyDataBase(getActivity());
        List<Order> order = db.getOrdersUser();
        Log.i("Myapp","Кол-во заявок: " + order.size());
        OrderAdapter mAdapter = new OrderAdapter(order);
        mAdapter.setActivity(getActivity());
        mAdapter.setOnClickOrderListener(new OrderAdapter.OnClickOrderListener() {
            @Override
            public void OnClicked(View itemView, int position) {
                if(iO==null){
                    iO = new InfoOrder();
                }
                iO.setOnClicCloseListener(new InfoOrder.OnClickCloseListener() {
                    @Override
                    public void onClicked() {
                        fm.beginTransaction()
                                .remove(iO)
                                .commit();
                    }
                });
                fm.beginTransaction()
                        .add(R.id.frame_layout_my_order,iO)
                        .commit();
            }
        });
        rec.setAdapter(mAdapter);
//        JSONObject jsonObject = readJSONFromFile();
//        if(null != jsonObject){
//            try {
//                textView.setText(jsonObject.getString("pupil")+" "+jsonObject.getString("data"));
//            }catch (JSONException e){
//                Toast.makeText(getContext(),"All bad with json",Toast.LENGTH_SHORT).show();
//            }
//        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fm = getChildFragmentManager();
    }

    private JSONObject readJSONFromFile(){
        String jsonString = null;
        FileInputStream FIS = null;
        try {
            File file = new File(getContext().getFilesDir() + "order");
            FIS = new FileInputStream(file);
            try{
                FileChannel fc = FIS.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size());
                jsonString = Charset.defaultCharset().decode(bb).toString();
                FIS.close();
            }catch(Exception e){
                Toast.makeText(getContext(),"Что-то пошло не так!",Toast.LENGTH_SHORT).show();
            }
        }catch (FileNotFoundException e){
            Toast.makeText(getContext(),"Файл не существует",Toast.LENGTH_SHORT).show();
        }
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(jsonString);
        }catch (Exception e){
            Toast.makeText(getContext(),"JSON Не создан",Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

}


