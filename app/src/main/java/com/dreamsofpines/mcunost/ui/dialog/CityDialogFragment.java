package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.editable;

/**
 * Created by ThePupsick on 11.03.2018.
 */

public class CityDialogFragment extends DialogFragment {


    private View view;
    private TextView listTxt, txtInfo;
    private ListView mListView;
    private AppCompatEditText editText;
    private String[] items;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;
    private Button mButton;
    private String city;
    public static OnCityChangedListener mListener;

    public interface OnCityChangedListener{
        void onChange(String city);
    }


    public static CityDialogFragment newInstance(OnCityChangedListener listener, String city){
        CityDialogFragment cF = new CityDialogFragment();
        cF.init(listener,city);
        return cF;
    }

    public void init(OnCityChangedListener listener,String city){
        this.mListener = listener;
        this.city = city;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_city,container,false);
        bindDialogFragment();
        initList();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Вы не выбрали город!",Toast.LENGTH_SHORT)
                            .show();
                }else {
                    mListener.onChange(editText.getText().toString());
                    dismiss();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equalsIgnoreCase("")){
                    initList();
                }else{
                    if(i1 > i2) {
                        initList();
                        searchItem(charSequence.toString());
                    }else{
                        searchItem(charSequence.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hideKeybord();
        return view;
    }


    private void bindDialogFragment(){
        mListView = (ListView) view.findViewById(R.id.list_city);
        txtInfo   = (TextView) view.findViewById(R.id.mess_city);
        editText  = (AppCompatEditText) view.findViewById(R.id.input_city_edit);
        mButton   = (Button) view.findViewById(R.id.butt_ok_city);
    }


    public void searchItem(String textToSearch){
        if(!listItems.isEmpty()) {
            for (String item : items) {
                if (!item.toLowerCase().contains(textToSearch.toLowerCase())) {
                    listItems.remove(item);
                }
            }
        }else{
            mListView.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    public void initList(){
        mListView.setVisibility(View.VISIBLE);
        items     = new String[]{"Другой регион","Москва","Санкт-Петербург","Новокузнецк","Казань", "Калининград"};
        listItems = new ArrayList<>(Arrays.asList(items));
        adapter   = new ArrayAdapter<>(getContext(), R.layout.item_list_city, R.id.item_txt, listItems);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listTxt = (TextView) view.findViewById(R.id.item_txt);
                editText.setText(listTxt.getText().toString());
                mListView.setVisibility(View.GONE);
                hideKeybord();
            }
        });
    }

    public void hideKeybord(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),0);
    }




}
