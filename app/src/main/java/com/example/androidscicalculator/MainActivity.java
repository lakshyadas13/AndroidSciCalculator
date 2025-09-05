package com.example.androidscicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;

    private final StringBuilder input = new StringBuilder();
    private String operator = null;
    private double firstNum = Double.NaN;
    private boolean resetOnNextDigit = false;

    private final DecimalFormat df = new DecimalFormat("0.######");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Digits & dot
        int[] digitIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };
        View.OnClickListener digitListener = v -> {
            if (resetOnNextDigit) {
                input.setLength(0);
                resetOnNextDigit = false;
            }
            CharSequence t = ((MaterialButton) v).getText();
            input.append(t);
            tvDisplay.setText(input.toString());
        };
        for (int id : digitIds) findViewById(id).setOnClickListener(digitListener);

        // Operators
        setOperatorHandler(R.id.btnAdd, "+");
        setOperatorHandler(R.id.btnSub, "-");
        setOperatorHandler(R.id.btnMul, "*");
        setOperatorHandler(R.id.btnDiv, "/");
        setOperatorHandler(R.id.btnPow, "pow");

        // Equals
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());

        // Scientific (unary)
        findViewById(R.id.btnSqrt).setOnClickListener(v -> applyUnary("sqrt"));
        findViewById(R.id.btnSin).setOnClickListener(v -> applyUnary("sin"));
        findViewById(R.id.btnCos).setOnClickListener(v -> applyUnary("cos"));
        findViewById(R.id.btnTan).setOnClickListener(v -> applyUnary("tan"));
        findViewById(R.id.btnLog).setOnClickListener(v -> applyUnary("log"));
        findViewById(R.id.btnLn).setOnClickListener(v -> applyUnary("ln"));

        // Conversions
        findViewById(R.id.btnCmToFt).setOnClickListener(v -> convertCmToFt());
        findViewById(R.id.btnKToC).setOnClickListener(v -> convertKToC());
        // Conversions
        findViewById(R.id.btnCmToFt).setOnClickListener(v -> convertCmToFt());
        findViewById(R.id.btnKToC).setOnClickListener(v -> convertKToC());
        findViewById(R.id.btnCToF).setOnClickListener(v -> convertCToF());
        findViewById(R.id.btnKgToLb).setOnClickListener(v -> convertKgToLb());

    }

    private void setOperatorHandler(int btnId, String op) {
        findViewById(btnId).setOnClickListener(v -> {
            if (input.length() == 0) return;
            firstNum = safeParse(input.toString());
            operator = op;
            input.setLength(0);
        });
    }

    private void calculate() {
        if (operator == null || input.length() == 0) return;

        double second = safeParse(input.toString());
        double result;

        switch (operator) {
            case "+": result = firstNum + second; break;
            case "-": result = firstNum - second; break;
            case "*": result = firstNum * second; break;
            case "/":
                if (second == 0) { showError(); return; }
                result = firstNum / second; break;
            case "pow": result = Math.pow(firstNum, second); break;
            default: return;
        }

        showNumber(result);
        operator = null;
        firstNum = result;
        resetOnNextDigit = true;
    }

    private void applyUnary(String func) {
        if (input.length() == 0) return;
        double val = safeParse(input.toString());
        double res;

        switch (func) {
            case "sqrt":
                if (val < 0) { showError(); return; }
                res = Math.sqrt(val); break;
            case "sin": res = Math.sin(Math.toRadians(val)); break;
            case "cos": res = Math.cos(Math.toRadians(val)); break;
            case "tan": res = Math.tan(Math.toRadians(val)); break;
            case "log":
                if (val <= 0) { showError(); return; }
                res = Math.log10(val); break;
            case "ln":
                if (val <= 0) { showError(); return; }
                res = Math.log(val); break;
            default: return;
        }

        showNumber(res);
        input.setLength(0);
        input.append(df.format(res));
        resetOnNextDigit = true;
    }

    private void convertCmToFt() {
        if (input.length() == 0) return;
        double cm = safeParse(input.toString());
        double ft = cm / 30.48;
        tvDisplay.setText(df.format(ft) + " ft");
        input.setLength(0);
        resetOnNextDigit = true;
        operator = null;
    }

    private void convertKToC() {
        if (input.length() == 0) return;
        double k = safeParse(input.toString());
        double c = k - 273.15;
        tvDisplay.setText(df.format(c) + " °C");
        input.setLength(0);
        resetOnNextDigit = true;
        operator = null;
    }

    private void clearAll() {
        input.setLength(0);
        operator = null;
        firstNum = Double.NaN;
        tvDisplay.setText("0");
        resetOnNextDigit = false;
    }

    private void showNumber(double n) {
        tvDisplay.setText(df.format(n));
    }

    private void showError() {
        tvDisplay.setText("Error");
        input.setLength(0);
        operator = null;
        firstNum = Double.NaN;
        resetOnNextDigit = true;
    }

    // Add these two new methods inside MainActivity.java

    private void convertCToF() {
        if (input.length() == 0) return;
        double c = safeParse(input.toString());
        double f = (c * 9/5) + 32;
        tvDisplay.setText(df.format(f) + " °F");
        input.setLength(0);
        resetOnNextDigit = true;
        operator = null;
    }

    private void convertKgToLb() {
        if (input.length() == 0) return;
        double kg = safeParse(input.toString());
        double lb = kg * 2.20462;
        tvDisplay.setText(df.format(lb) + " lb");
        input.setLength(0);
        resetOnNextDigit = true;
        operator = null;
    }


    private double safeParse(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { showError(); return 0; }
    }
}
