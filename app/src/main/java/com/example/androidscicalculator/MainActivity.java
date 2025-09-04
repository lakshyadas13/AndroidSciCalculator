package com.example.androidscicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etNum1, etNum2;
    Button btnAdd, btnSub, btnMul, btnDiv;
    Button btnSqrt, btnPow, btnSin, btnCos, btnTan, btnLog, btnLn;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btnSqrt = findViewById(R.id.btnSqrt);
        btnPow = findViewById(R.id.btnPow);
        btnSin = findViewById(R.id.btnSin);
        btnCos = findViewById(R.id.btnCos);
        btnTan = findViewById(R.id.btnTan);
        btnLog = findViewById(R.id.btnLog);
        btnLn = findViewById(R.id.btnLn);
        tvResult = findViewById(R.id.tvResult);

        // Basic operations
        btnAdd.setOnClickListener(v -> calculate("+"));
        btnSub.setOnClickListener(v -> calculate("-"));
        btnMul.setOnClickListener(v -> calculate("*"));
        btnDiv.setOnClickListener(v -> calculate("/"));

        // Scientific operations
        btnSqrt.setOnClickListener(v -> scientific("sqrt"));
        btnPow.setOnClickListener(v -> scientific("pow"));
        btnSin.setOnClickListener(v -> scientific("sin"));
        btnCos.setOnClickListener(v -> scientific("cos"));
        btnTan.setOnClickListener(v -> scientific("tan"));
        btnLog.setOnClickListener(v -> scientific("log"));
        btnLn.setOnClickListener(v -> scientific("ln"));
    }

    private void calculate(String op) {
        String s1 = etNum1.getText().toString();
        String s2 = etNum2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            tvResult.setText("Enter both numbers");
            return;
        }

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double res = 0;

        switch (op) {
            case "+": res = n1 + n2; break;
            case "-": res = n1 - n2; break;
            case "*": res = n1 * n2; break;
            case "/":
                if (n2 == 0) {
                    tvResult.setText("Cannot divide by zero");
                    return;
                }
                res = n1 / n2;
                break;
        }
        tvResult.setText("Result: " + res);
    }

    private void scientific(String op) {
        String s1 = etNum1.getText().toString();
        String s2 = etNum2.getText().toString();
        double n1, n2;
        double res = 0;

        switch (op) {
            case "sqrt":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                res = Math.sqrt(n1);
                break;

            case "pow":
                if (s1.isEmpty() || s2.isEmpty()) {
                    tvResult.setText("Enter both numbers (x and y)");
                    return;
                }
                n1 = Double.parseDouble(s1);
                n2 = Double.parseDouble(s2);
                res = Math.pow(n1, n2);
                break;

            case "sin":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                res = Math.sin(Math.toRadians(n1));
                break;

            case "cos":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                res = Math.cos(Math.toRadians(n1));
                break;

            case "tan":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                res = Math.tan(Math.toRadians(n1));
                break;

            case "log":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                if (n1 <= 0) {
                    tvResult.setText("Log only valid for positive numbers");
                    return;
                }
                res = Math.log10(n1);
                break;

            case "ln":
                if (s1.isEmpty()) {
                    tvResult.setText("Enter number in Num1");
                    return;
                }
                n1 = Double.parseDouble(s1);
                if (n1 <= 0) {
                    tvResult.setText("Ln only valid for positive numbers");
                    return;
                }
                res = Math.log(n1);
                break;
        }
        tvResult.setText("Result: " + res);
    }
}