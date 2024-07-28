package com.example.glucideaide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String weight = etWeight.getText().toString();
                String height = etHeight.getText().toString();

                if (weight.isEmpty() || height.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the weight and height", Toast.LENGTH_SHORT).show();
                } else {
                    float weightValue = Float.parseFloat(weight);
                    float heightValue = Float.parseFloat(height) / 100;
                    float bmi = weightValue / (heightValue * heightValue);

                    tvResult.setText("Votre IMC est: " + bmi);
                }
            }
        });
    }
}

