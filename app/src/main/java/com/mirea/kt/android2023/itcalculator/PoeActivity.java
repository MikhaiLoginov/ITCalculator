package com.mirea.kt.android2023.itcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PoeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poe);

        EditText pairsEditText = findViewById(R.id.pairsEditText);
        EditText cableSizeEditText = findViewById(R.id.cableSizeEditText);
        EditText supplyVoltageEditText = findViewById(R.id.supplyVoltageEditText);
        EditText cableLengthEditText = findViewById(R.id.cableLengthEditText);
        EditText powerPerDeviceEditText = findViewById(R.id.powerPerDeviceEditText);
        EditText totalDevicesEditText = findViewById(R.id.totalDevicesEditText);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(v -> {
            try {
                double voltageDrop = calculate(
                        Integer.parseInt(pairsEditText.getText().toString()),
                        Integer.parseInt(cableSizeEditText.getText().toString()),
                        Double.parseDouble(supplyVoltageEditText.getText().toString()),
                        Double.parseDouble(cableLengthEditText.getText().toString()),
                        Double.parseDouble(powerPerDeviceEditText.getText().toString()),
                        Integer.parseInt(totalDevicesEditText.getText().toString())
                );

                resultTextView.setText(getString(R.string.poe_result, voltageDrop));
            } catch (Exception ignored) {}
        });
    }

    public double calculate(
            int pairs,
            int cableSize,
            double supplyVoltage,
            double cableLength,
            double powerPerDevice,
            int totalDevices
    ) {
        // Удельное сопротивление меди в Ом.м
        double resistivityCopper = 1.68 * Math.pow(10, -8);
        // Ток на пару в амперах
        double currentPerPair = powerPerDevice / (supplyVoltage / pairs);
        // Размер кабеля в мм
        double cableSizeInMm = 0.127 * Math.pow(92, (36 - cableSize) / 39.0);
        // Сопротивление кабеля в Ом
        double resistance = (resistivityCopper * cableLength) / (pairs * Math.pow(cableSizeInMm / 1000, 2));

        // Суммарный ток в цепи
        double totalCurrent = currentPerPair * totalDevices;

        // Расчет падения напряжения по закону Ома (V = I * R)
        return totalCurrent * resistance;
    }
}

