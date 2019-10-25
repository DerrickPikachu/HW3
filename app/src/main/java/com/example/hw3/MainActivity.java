package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String operation = "0";
    private TextView operationTxv;
    private Button clearBtn;
    private int[] numberBtns = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4
                                , R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private int[] operatorBtns = {R.id.btnPlus, R.id.btnMinus, R.id.btnMul, R.id.btnDiv};
    private boolean isPreOp = false;
    private boolean isFirstZero = true;
    private boolean isOperated = false;
    private boolean error = false;

    private void setOperatorAvailable(boolean set) {
        for (int i : operatorBtns)
            findViewById(i).setClickable(set);
        findViewById(R.id.btnEqual).setClickable(set);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationTxv = findViewById(R.id.numberTxv);
        operation = operationTxv.getText().toString();
        clearBtn = findViewById(R.id.btnC);

        for (int i : numberBtns)
            findViewById(i).setOnClickListener(this);

        for (int i : operatorBtns)
            findViewById(i).setOnClickListener(this);

        findViewById(R.id.btnEqual).setOnClickListener(this);

        clearBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.btnC) {
            String nextInput = ((Button)view).getText().toString();

            if (nextInput.compareTo(getResources().getString(R.string.equal)) == 0) {
                //get the operation result
                try {
                    Arithmetic arithmetic = new Arithmetic(operation);
                    operation = Integer.toString(arithmetic.getResult());
                    isOperated = true;
                }
                catch (ArithmeticException e){
                    operation = "CAN NOT DIVIDED BY ZERO";
                    isOperated = true;
                    error = true;

                    setOperatorAvailable(false);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            else if (isOperated && Character.isDigit(nextInput.charAt(0))) {
                operation = nextInput;
                isOperated = false;

                if (error) {
                    error = false;
                    setOperatorAvailable(true);
                }
            }
            else if (isFirstZero && Character.isDigit(nextInput.charAt(0))) {
                operation = operation.substring(0, operation.length() - 1) + nextInput;
            }
            else {
                if (!Character.isDigit(nextInput.charAt(0))) {
                    if (!isPreOp)
                        operation = operation + nextInput;
                }
                else {
                    operation = operation + nextInput;
                }

                isOperated = false;
            }

            isFirstZero = ((isPreOp || isFirstZero) && nextInput.compareTo("0") == 0)? true:false;
            isPreOp = (Character.isDigit(nextInput.charAt(0)))? false:true;
        }
        else {
            operation = "0";
            isPreOp = false;
            isFirstZero = true;
            isOperated = false;
            setOperatorAvailable(true);
        }

        operationTxv.setText(operation);
    }
}
