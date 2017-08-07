package com.dreamsofpines.mcunost.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;

/**
 * Created by ThePupsick on 07.08.17.
 */

public class StockActivity extends AppCompatActivity {

    private String stockOne = "\n«ПРИГЛАСИ ДРУГА!»\n" +
            "\n" +
            "Молодежному центру ЮНОСТЬ нужны такие руководители групп, как Вы!\n" +
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
    private String stockTwo = "\"СЧАСТЛИВЫЙ БОНУС\"\n" +
            "\n" +
            "Теперь за каждую совершенную поездку Вы получаете бонус!\n" +
            "\n" +
            "Информацию о конкретном начислении Вы получите в виде смс-сообщения.\n" +
            "\n" +
            "Счастливых Вам поездок!\n";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        final LeftMenu leftMenu = new LeftMenu(this);
        leftMenu.build(this,1);
        TextView textView = (TextView) findViewById(R.id.txt_stock);
        textView.setText(stockOne+stockTwo);
        Button button = (Button) findViewById(R.id.stock_button_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftMenu.openMenu();
            }
        });
    }
}
