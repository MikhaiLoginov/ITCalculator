package com.mirea.kt.android2023.itcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubnetActivity extends AppCompatActivity {

    EditText ipEditText;
    EditText maskEditText;
    Button calculateButton;
    TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet);

        ipEditText = findViewById(R.id.ipEditText);
        maskEditText = findViewById(R.id.maskEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        int[] ip;
        int[] mask;
        try {
            ip = new int[4];
            String[] ipParts = ipEditText.getText().toString().split("\\.");
            for (int i = 0; i < 4; i++) {
                ip[i] = Integer.parseInt(ipParts[i]);
            }

            mask = new int[4];
            String maskText = maskEditText.getText().toString();
            try {
                mask = convertNetworkMask(Integer.parseInt(maskText));
            } catch (Exception e) {
                String[] maskParts = maskText.split("\\.");
                for (int i = 0; i < 4; i++) {
                    mask[i] = Integer.parseInt(maskParts[i]);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Некорректные данные", Toast.LENGTH_SHORT).show();
            return;
        }

        String networkAddress = formatAddress(calculateNetworkAddress(ip, mask));
        String broadcastAddress = formatAddress(calculateBroadcastAddress(ip, mask));
        int totalHosts = calculateTotalHosts(mask);

        String r = getString(R.string.subnet_result, networkAddress, broadcastAddress, totalHosts);
        resultTextView.setText(r);

        int[] a = new int[] { 2, 1, 1, 1, 1 };
    }

    public static int[] convertNetworkMask(int cidr) {
        int[] mask = new int[4];

        for (int i = 0; i < 4; i++) {
            if (cidr >= 8) {
                mask[i] = 255;
                cidr -= 8;
            } else if (cidr > 0) {
                mask[i] = 256 - (int) Math.pow(2, 8 - cidr);
                cidr = 0;
            } else {
                mask[i] = 0;
            }
        }

        return mask;
    }

    public int[] calculateNetworkAddress(int[] ip, int[] mask) {
        int[] networkAddress = new int[4];

        for (int i = 0; i < 4; i++) {
            networkAddress[i] = ip[i] & mask[i];
        }

        return networkAddress;
    }

    public int[] calculateBroadcastAddress(int[] ip, int[] mask) {
        int[] broadcastAddress = new int[4];

        for (int i = 0; i < 4; i++) {
            broadcastAddress[i] = ip[i] | (255 - mask[i]);
        }

        return broadcastAddress;
    }

    public int calculateTotalHosts(int[] mask) {
        int totalBits = 0;

        for (int i = 0; i < 4; i++) {
            totalBits += Integer.bitCount(mask[i]);
        }

        return (int) Math.pow(2, 32 - totalBits) - 2;
    }

    public static String formatAddress(int[] address) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            builder.append(address[i]);

            if (i < 3) {
                builder.append(".");
            }
        }

        return builder.toString();
    }
}