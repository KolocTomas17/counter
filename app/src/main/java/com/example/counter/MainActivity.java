package com.example.counter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int count250 = 0;
    private int count500 = 0;
    private int count750 = 0;

    private int dailyGoal = 3000;

    private TextView tvCount250, tvCount500, tvCount750, tvTotalMl, tvGoalInfo;
    private EditText etGoal;
    private Button btnResetAll, btnSetGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount250 = findViewById(R.id.tvCount250);
        tvCount500 = findViewById(R.id.tvCount500);
        tvCount750 = findViewById(R.id.tvCount750);
        tvTotalMl = findViewById(R.id.tvTotalMl);

        tvGoalInfo = findViewById(R.id.tvGoalInfo);
        etGoal = findViewById(R.id.etGoal);
        btnSetGoal = findViewById(R.id.btnSetGoal);

        SharedPreferences prefs = getSharedPreferences("counter_prefs", MODE_PRIVATE);
        dailyGoal = prefs.getInt("dailyGoal", 3000);
        etGoal.setText(String.valueOf(dailyGoal));

        Button btnAdd250 = findViewById(R.id.btnAdd250);
        Button btnRemove250 = findViewById(R.id.btnRemove250);
        Button btnAdd500 = findViewById(R.id.btnAdd500);
        Button btnRemove500 = findViewById(R.id.btnRemove500);
        Button btnAdd750 = findViewById(R.id.btnAdd750);
        Button btnRemove750 = findViewById(R.id.btnRemove750);

        btnSetGoal.setOnClickListener(v -> {
            String goalText = etGoal.getText().toString();
            if (!goalText.isEmpty()) {
                dailyGoal = Integer.parseInt(goalText);
                // Uložení cíle
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("dailyGoal", dailyGoal);
                editor.apply();
                updateUI();
            }
        });

        updateUI(); // První vykreslení



        btnAdd250.setOnClickListener(v -> {
            count250++;
            updateUI();
        });

        btnRemove250.setOnClickListener(v -> {
            if (count250 > 0) count250--;
            updateUI();
        });

        btnAdd500.setOnClickListener(v -> {
            count500++;
            updateUI();
        });

        btnRemove500.setOnClickListener(v -> {
            if (count500 > 0) count500--;
            updateUI();
        });

        btnAdd750.setOnClickListener(v -> {
            count750++;
            updateUI();
        });

        btnRemove750.setOnClickListener(v -> {
            if (count750 > 0) count750--;
            updateUI();
        });
    }

    private void updateUI() {
        tvCount250.setText(String.valueOf(count250));
        tvCount500.setText(String.valueOf(count500));
        tvCount750.setText(String.valueOf(count750));
        int totalMl = (count250 * 250) + (count500 * 500) + (count750 * 750);
        int remaining = dailyGoal - totalMl;
        if (remaining > 0) {
            tvGoalInfo.setText("Zbývá vypít: " + remaining + " ml");
        } else {
            tvGoalInfo.setText("Cíl splněn!");
        }


        tvTotalMl.setText("Vypito celkem: " + totalMl + " ml");

        Button btnResetAll = findViewById(R.id.btnResetAll);
        btnResetAll.setOnClickListener(v -> {
            count250 = 0;
            count500 = 0;
            count750 = 0;
            updateUI();
        });
    }
}