package com.example.t9a_design;

import static com.example.t9a_design.calculation.calculator.ChargeCalculator.chargeRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.t9a_design.calculation.filter.InputFilterMinMax;
import com.example.t9a_design.calculation.model.rollStuff.charge.ChargeReRoll;
import com.example.t9a_design.calculation.model.rollStuff.charge.MinMax;

public class ChargeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText distanceInput;

    Spinner spinnerMinMax;
    Spinner spinnerChargeRR;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        distanceInput = findViewById(R.id.distanceInput);
        distanceInput.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
        distanceInput.setOnClickListener(view -> {
            if (!distanceInput.getText().toString().equals("")) {
                refreshChance(distanceInput);
            }
        });

        spinnerMinMax = findViewById(R.id.spinnerMinMax);
        spinnerMinMax.setAdapter(new ArrayAdapter<MinMax>
                (this, android.R.layout.simple_spinner_item, MinMax.values()));
        spinnerMinMax.setOnItemSelectedListener(this);
        spinnerMinMax.setSelection(1);

        spinnerChargeRR = findViewById(R.id.spinnerChargeRR);
        spinnerChargeRR.setAdapter(new ArrayAdapter<ChargeReRoll>
                (this, android.R.layout.simple_spinner_item, ChargeReRoll.values()));
        spinnerChargeRR.setOnItemSelectedListener(this);
        spinnerChargeRR.setSelection(0);


    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    Intent i = new Intent(ChargeActivity.this, MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!distanceInput.getText().toString().equals("")) {
            refreshChance(adapterView);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void refreshChance(View v) {

        int needed = Integer.parseInt(distanceInput.getText().toString());
        MinMax minMax = (MinMax) spinnerMinMax.getSelectedItem();
        ChargeReRoll chRR = (ChargeReRoll) spinnerChargeRR.getSelectedItem();

        float chance = chargeRoll(needed, minMax, chRR);
        String result = "Chance: " + chance;
        ((TextView) findViewById(R.id.chanceResult)).setText(result);

    }
}