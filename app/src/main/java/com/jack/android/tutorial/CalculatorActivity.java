package com.jack.android.tutorial;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jack.android.tutorial.algorithms.calculator.Calculator;
import com.jack.android.tutorial.algorithms.calculator.Operator;
import com.jack.android.tutorial.algorithms.calculator.SimpleCalculator;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.OperatorNotSupportedException;


public class CalculatorActivity extends ActionBarActivity {

    private static final String TAG = CalculatorActivity.class.getSimpleName();

    private Calculator<Double> mCalculator;

    // controls
    private EditText mEditText;
    private Button mButtonNumber0;
    private Button mButtonNumber1;
    private Button mButtonNumber2;
    private Button mButtonNumber3;
    private Button mButtonNumber4;
    private Button mButtonNumber5;
    private Button mButtonNumber6;
    private Button mButtonNumber7;
    private Button mButtonNumber8;
    private Button mButtonNumber9;
    private Button mButtonNumberPoint;

    private Button mButtonOperatorPlus;
    private Button mButtonOperatorMinus;
    private Button mButtonOperatorMultiple;
    private Button mButtonOperatorDivisor;
    private Button mButtonOperatorEqual;

    private Button mButtonClear;
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_number_0:
                case R.id.button_number_1:
                case R.id.button_number_2:
                case R.id.button_number_3:
                case R.id.button_number_4:
                case R.id.button_number_5:
                case R.id.button_number_6:
                case R.id.button_number_7:
                case R.id.button_number_8:
                case R.id.button_number_9:
                case R.id.button_number_point:
                    mEditText.setText(mEditText.getText() + v.getTag().toString());
                    break;

                case R.id.button_operator_plus:
                case R.id.button_operator_minus:
                case R.id.button_operator_multiple:
                case R.id.button_operator_divisor:
                    try {
                        mCalculator.setNextOperand(mEditText.getText().toString());
                        mCalculator.setNextOperator((Operator<Double>) v.getTag());
                        mEditText.setText("");
                    } catch (IllegalOperandException e) {
                        e.printStackTrace();
                    } catch (OperatorNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.button_operator_equal:
                    try {
                        mCalculator.setNextOperand(mEditText.getText().toString());
                        mEditText.setText("" + mCalculator.calculate());
                    } catch (IllegalOperandException e) {
                        e.printStackTrace();
                        Toast.makeText(CalculatorActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    } catch (IllegalOperatorException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    };


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_calculator, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
private View.OnClickListener mClearButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mCalculator.clear();
        mEditText.setText("");
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mCalculator = new SimpleCalculator();

        mEditText = (EditText) findViewById(R.id.edit_text);

        mButtonNumber0 = (Button) findViewById(R.id.button_number_0);
        mButtonNumber1 = (Button) findViewById(R.id.button_number_1);
        mButtonNumber2 = (Button) findViewById(R.id.button_number_2);
        mButtonNumber3 = (Button) findViewById(R.id.button_number_3);
        mButtonNumber4 = (Button) findViewById(R.id.button_number_4);
        mButtonNumber5 = (Button) findViewById(R.id.button_number_5);
        mButtonNumber6 = (Button) findViewById(R.id.button_number_6);
        mButtonNumber7 = (Button) findViewById(R.id.button_number_7);
        mButtonNumber8 = (Button) findViewById(R.id.button_number_8);
        mButtonNumber9 = (Button) findViewById(R.id.button_number_9);
        mButtonNumber0 = (Button) findViewById(R.id.button_number_0);
        mButtonNumberPoint = (Button) findViewById(R.id.button_number_point);

        mButtonNumber0.setTag("0");
        mButtonNumber1.setTag("1");
        mButtonNumber2.setTag("2");
        mButtonNumber3.setTag("3");
        mButtonNumber4.setTag("4");
        mButtonNumber5.setTag("5");
        mButtonNumber6.setTag("6");
        mButtonNumber7.setTag("7");
        mButtonNumber8.setTag("8");
        mButtonNumber9.setTag("9");
        mButtonNumberPoint.setTag(".");

        mButtonNumber0.setOnClickListener(mButtonClickListener);
        mButtonNumber1.setOnClickListener(mButtonClickListener);
        mButtonNumber2.setOnClickListener(mButtonClickListener);
        mButtonNumber3.setOnClickListener(mButtonClickListener);
        mButtonNumber4.setOnClickListener(mButtonClickListener);
        mButtonNumber5.setOnClickListener(mButtonClickListener);
        mButtonNumber6.setOnClickListener(mButtonClickListener);
        mButtonNumber7.setOnClickListener(mButtonClickListener);
        mButtonNumber8.setOnClickListener(mButtonClickListener);
        mButtonNumber9.setOnClickListener(mButtonClickListener);
        mButtonNumberPoint.setOnClickListener(mButtonClickListener);

        mButtonOperatorPlus = (Button) findViewById(R.id.button_operator_plus);
        mButtonOperatorMinus = (Button) findViewById(R.id.button_operator_minus);
        mButtonOperatorMultiple = (Button) findViewById(R.id.button_operator_multiple);
        mButtonOperatorDivisor = (Button) findViewById(R.id.button_operator_divisor);
        mButtonOperatorEqual = (Button) findViewById(R.id.button_operator_equal);

        mButtonOperatorPlus.setTag(SimpleCalculator.OPERATOR_PLUS);
        mButtonOperatorMinus.setTag(SimpleCalculator.OPERATOR_MINUS);
        mButtonOperatorMultiple.setTag(SimpleCalculator.OPERATOR_MULTIPLE);
        mButtonOperatorDivisor.setTag(SimpleCalculator.OPERATOR_DIVISION);

        mButtonOperatorPlus.setOnClickListener(mButtonClickListener);
        mButtonOperatorMinus.setOnClickListener(mButtonClickListener);
        mButtonOperatorMultiple.setOnClickListener(mButtonClickListener);
        mButtonOperatorDivisor.setOnClickListener(mButtonClickListener);
        mButtonOperatorEqual.setOnClickListener(mButtonClickListener);

        mButtonClear = (Button) findViewById(R.id.button_operator_clear);

        mButtonClear.setOnClickListener(mClearButtonClickListener);

//        try {
//            mCalculator.setNextOperand("1");
//            mCalculator.setNextOperand("aaa12");
//            mCalculator.setNextOperator(SimpleCalculator.OPERATOR_PLUS);
//            Log.v(TAG, "" + mCalculator.calculate());
//        } catch (IllegalOperandException e) {
//            e.printStackTrace();
//        } catch (OperatorNotSupportedException e) {
//            e.printStackTrace();
//        } catch (IllegalOperatorException e) {
//            e.printStackTrace();
//        }
    }
}
