package com.mirea.kt.android2023.itcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;

public class PasswordActivity extends AppCompatActivity {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";
    private static final String ALL_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        EditText lengthEditText = findViewById(R.id.lengthEditText);
        Button generateButton = findViewById(R.id.generateButton);
        TextView resultTextView = findViewById(R.id.resultTextView);
        Button copyButton = findViewById(R.id.copyButton);

        generateButton.setOnClickListener(v -> {
            try {
                int length = Integer.parseInt(lengthEditText.getText().toString());
                resultTextView.setText(generatePassword(length));
            } catch (Exception ignored) {}
        });

        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", resultTextView.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.password_copied, Toast.LENGTH_SHORT).show();
        });
    }

    public String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_CHARS.length());
            password.append(ALL_CHARS.charAt(randomIndex));
        }

        return password.toString();
    }
}