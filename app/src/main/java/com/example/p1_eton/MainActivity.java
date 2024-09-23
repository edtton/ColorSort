package com.example.p1_eton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickStartGame(View v) {
        Intent intent = new Intent(this, MainActivity2.class);

        if (v.getId() == R.id.buttonEasy) {
            intent.putExtra("easy", true);
        }
        if (v.getId() == R.id.buttonHard) {
            intent.putExtra("easy", false);
        }

        startActivity(intent);
    }
}