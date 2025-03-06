package com.example.e_kuisioner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionnaireActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    RadioGroup tokopediaColorGroups[];
    RadioGroup tokopediaNavGroups[];
    RadioGroup shopeeColorGroups[];
    RadioGroup shopeeNavGroups[];

    Button submitButton;
    Button nextButtonWarnaTokopedia;
    Button nextButtonWarnaShopee;
    Button nextButtonNavigasiTokopedia;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        myDb = new DatabaseHelper(this);

        userId = getIntent().getStringExtra("USER_ID");
        if (userId == null) {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tokopediaColorGroups = new RadioGroup[]{
                findViewById(R.id.tokopedia_color_1),
                findViewById(R.id.tokopedia_color_2),
                findViewById(R.id.tokopedia_color_3),
                findViewById(R.id.tokopedia_color_4),
                findViewById(R.id.tokopedia_color_5)
        };

        tokopediaNavGroups = new RadioGroup[]{
                findViewById(R.id.tokopedia_nav_1),
                findViewById(R.id.tokopedia_nav_2),
                findViewById(R.id.tokopedia_nav_3),
                findViewById(R.id.tokopedia_nav_4),
                findViewById(R.id.tokopedia_nav_5)
        };

        shopeeColorGroups = new RadioGroup[]{
                findViewById(R.id.shopee_color_1),
                findViewById(R.id.shopee_color_2),
                findViewById(R.id.shopee_color_3),
                findViewById(R.id.shopee_color_4),
                findViewById(R.id.shopee_color_5)
        };

        shopeeNavGroups = new RadioGroup[]{
                findViewById(R.id.shopee_nav_1),
                findViewById(R.id.shopee_nav_2),
                findViewById(R.id.shopee_nav_3),
                findViewById(R.id.shopee_nav_4),
                findViewById(R.id.shopee_nav_5)
        };

        submitButton = findViewById(R.id.submit_all);
        View tokopediaSectionWarna = findViewById(R.id.tokopedia_section_warna_tokopedia);
        View tokopediaSectionNavigasi = findViewById(R.id.tokopedia_section_navigasi_tokopedia);
        View shopeeSectionWarna = findViewById(R.id.shopee_section_warna_shopee);
        View shopeeSectionNavigasi = findViewById(R.id.shopee_section_navigasi_shopee);
        nextButtonWarnaTokopedia = findViewById(R.id.next_button_warna_tokopedia);
        nextButtonWarnaShopee = findViewById(R.id.next_button_warna_shopee);
        nextButtonNavigasiTokopedia = findViewById(R.id.next_button_navigasi_tokopedia);

        tokopediaSectionNavigasi.setVisibility(View.GONE);
        shopeeSectionWarna.setVisibility(View.GONE);
        shopeeSectionNavigasi.setVisibility(View.GONE);

        nextButtonWarnaTokopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokopediaSectionWarna.setVisibility(View.GONE);
                tokopediaSectionNavigasi.setVisibility(View.VISIBLE);
            }
        });

        nextButtonNavigasiTokopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokopediaSectionNavigasi.setVisibility(View.GONE);
                shopeeSectionWarna.setVisibility(View.VISIBLE);
            }
        });

        nextButtonWarnaShopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopeeSectionWarna.setVisibility(View.GONE);
                shopeeSectionNavigasi.setVisibility(View.VISIBLE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tokopediaColorTotal = getTotalCheckedValue(tokopediaColorGroups);
                int tokopediaNavTotal = getTotalCheckedValue(tokopediaNavGroups);
                int shopeeColorTotal = getTotalCheckedValue(shopeeColorGroups);
                int shopeeNavTotal = getTotalCheckedValue(shopeeNavGroups);

                if (tokopediaColorTotal == -1 || tokopediaNavTotal == -1 || shopeeColorTotal == -1 || shopeeNavTotal == -1) {
                    Toast.makeText(QuestionnaireActivity.this, "Please answer all questions", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });
    }

    private int getTotalCheckedValue(RadioGroup[] groups) {
        int totalValue = 0;

        for (RadioGroup group : groups) {
            int checkedRadioButtonId = group.getCheckedRadioButtonId();
            if (checkedRadioButtonId == -1) {
                return -1;
            }

            View radioButton = group.findViewById(checkedRadioButtonId);
            int idx = group.indexOfChild(radioButton);
            totalValue += idx + 1;
        }

        return totalValue;
    }
}
