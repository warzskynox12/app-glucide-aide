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

    // Declare the variables
    EditText etWeight, etHeight;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the variables
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);


        // Set a click listener on the button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Get the weight and height from the user
                String weight = etWeight.getText().toString();
                String height = etHeight.getText().toString();

                // Check if the weight and height are not empty
                if (weight.isEmpty() || height.isEmpty()) {
                    // Display a toast message
                    Toast.makeText(MainActivity.this, "Please enter the weight and height", Toast.LENGTH_SHORT).show();
                } else {
                    // Calculate the BMI
                    float weightValue = Float.parseFloat(weight);
                    float heightValue = Float.parseFloat(height) / 100;
                    float bmi = weightValue / (heightValue * heightValue);

                    // Display the result
                    tvResult.setText("Your BMI is: " + bmi);
                }
            }
        });
    }
}

