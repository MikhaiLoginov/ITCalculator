package com.mirea.kt.android2023.itcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity {

    EditText amountEditText;
    Spinner amountSpinner;
    EditText rateEditText;
    Spinner rateSpinner;
    Button calculateButton;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        amountEditText = findViewById(R.id.amountEditText);
        amountSpinner = findViewById(R.id.amountSpinner);
        rateEditText = findViewById(R.id.rateEditText);
        rateSpinner = findViewById(R.id.rateSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        try {
            long amount = Long.parseLong(amountEditText.getText().toString());
            long rate = Long.parseLong(rateEditText.getText().toString());

            long amountSpinnerPosition = amountSpinner.getSelectedItemPosition();
            long rateSpinnerPosition = rateSpinner.getSelectedItemPosition();

            long amountFactor = amountSpinnerPosition % 2 == 1 ? 1024 : 1000;
            amount *= Math.pow(amountFactor, (double) (amountSpinnerPosition / 2));

            long rateFactor = rateSpinnerPosition % 2 == 1 ? 1024 : 1000;
            rate *= Math.pow(rateFactor, (double) (rateSpinnerPosition / 2));

            long result = amount / rate;
            long hours = result / 3600;
            long minutes = result % 3600 / 60;
            long seconds = result % 60;
            resultTextView.setText(getString(R.string.transfer_result, result, hours, minutes, seconds));
        } catch (Exception ignored) {}
    }
}
