package com.dreamsofpines.mcunost.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mBarItem;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.SaleItem;
import com.dreamsofpines.mcunost.data.storage.models.TextItem;
import com.dreamsofpines.mcunost.ui.adapters.AdapterOrders;

import java.util.ArrayList;
import java.util.List;

public class LentaFragment extends Fragment {

    private View view;
    private static LentaFragment sLentaFragment;
    private FragmentManager fm;

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public static LentaFragment getInstance(FragmentManager fm){
        if(sLentaFragment == null){
            sLentaFragment = new LentaFragment();
            sLentaFragment.setFm(fm);
        }
        return sLentaFragment;
    }



    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private AdapterOrders mAdapterOrders;
    private SaleItem saleItemF;
    private SaleItem saleItemS;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_lenta,container,false);
        bindView();
        saleItemF = createSaleItem(1,"Пригласи друга",stockOne);
        saleItemS = createSaleItem(2,"Счастливый бонус",stockTwo);
        initRecyclerView();
        initSwipeRefresh();
        return view;
    }

    private void initSwipeRefresh(){
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
    }


    private void initRecyclerView(){
        mAdapterOrders = new AdapterOrders(getActivity());
        mAdapterOrders.setItems(getItemsForRecycler());
        mAdapterOrders.setListener(id -> {
            StockFragment stockFragment = new StockFragment();
            stockFragment.setSaleItem(id == 1? saleItemF : saleItemS);
            fm.beginTransaction()
                    .add(R.id.frame_main,stockFragment)
                    .addToBackStack("")
                    .commit();
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapterOrders);
    }


    private List<mItemRecyclerView> getItemsForRecycler(){
        List<mItemRecyclerView> list = new ArrayList<>();
        list.add(new TextItem().setText("Акции"));
        list.add(saleItemF);
        list.add(saleItemS);
        return list;
    }

    private SaleItem createSaleItem(int id,String title, String text){
        SaleItem saleItem = new SaleItem();
        saleItem.setIdSale(id);
        saleItem.setTitle(title);
        saleItem.setText(text);
        return saleItem;
    }

    private void bindView(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sale_recycler);
    }

    // так как данный я не получаю сервера они захардкожены здесь

    private String stockOne = "Молодежному центру ЮНОСТЬ нужны такие руководители групп, как Вы!\n" +
            "\n" +
            "Пригласите до 10 новых руководителей и получите 6000 рублей за каждого, когда он совершит первую поездку!\n" +
            "\n" +
            " Начните приглашать новых руководителей групп!\n" +
            "\n" +
            " Правила и условия:\n" +
            "\n" +
            "1. Порекомендуйте нашу компанию совершенно новому руководителю группы.\n" +
            "2. Как только поездка будет совершена этим руководителем, Вы получите 6000 рублей на счет Вашей виртуальной дисконтной карты.\n" +
            "3. Данные о пополнении Вашего счета или использовании денежных средств с него поступают Вам в виде смс-сообщений от отправителя YUNOST.\n" +
            "4. Вы можете также запросить данные о состоянии Вашего счета, позвонив по любому телефону компании.\n" +
            "5. Срок действия акции – до 1 июля 2017 года.\n\n";
    private String stockTwo = "Теперь за каждую совершенную поездку Вы получаете бонус!\n" +
            "\n" +
            "Информацию о конкретном начислении Вы получите в виде смс-сообщения.\n" +
            "\n" +
            "Счастливых Вам поездок!\n";


}
