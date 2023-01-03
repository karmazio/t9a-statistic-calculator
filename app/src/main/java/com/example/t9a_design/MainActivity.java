package com.example.t9a_design;

import static com.example.t9a_design.calculation.calculator.AttackCalculator.armourSaveRoll;
import static com.example.t9a_design.calculation.calculator.AttackCalculator.specialSaveRoll;
import static com.example.t9a_design.calculation.calculator.AttackCalculator.toHitRoll;
import static com.example.t9a_design.calculation.calculator.AttackCalculator.toWoundAfterPoison;
import static com.example.t9a_design.calculation.calculator.AttackCalculator.toWoundNoPoison;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.t9a_design.calculation.model.AttackRollSet;
import com.example.t9a_design.calculation.model.WoundSet;
import com.example.t9a_design.calculation.model.fraction.Fraction;
import com.example.t9a_design.calculation.model.rollStuff.ReRoll;
import com.example.t9a_design.calculation.model.rollStuff.singleRoll.D6Roll;
import com.example.t9a_design.calculation.model.rollStuff.singleRoll.SaveRoll;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText baseInput;

    Spinner spinnerToHit;
    Spinner spinnerToHitRR;

    Spinner spinnerToWound;
    Spinner spinnerToWoundRR;

    Spinner spinnerArmor;
    Spinner spinnerArmorRR;

    Spinner spinnerSpecial;
    Spinner spinnerSpecialRR;

    Switch switchPoison, switchBF, switchLS, switchSpecial;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseInput = findViewById(R.id.attacksInput);
        baseInput.setOnClickListener(view -> {
            if (!baseInput.getText().toString().equals("")) {
                refreshHits(baseInput);
            }
        });
        // To hit selector
        spinnerToHit = findViewById(R.id.spinnerToHit);
        spinnerToHit.setAdapter(new ArrayAdapter<D6Roll>
                (this, android.R.layout.simple_spinner_item, D6Roll.values()));
        spinnerToHit.setOnItemSelectedListener(this);
        spinnerToHit.setSelection(2);

        spinnerToHitRR = findViewById(R.id.spinnerToHitRR);
        spinnerToHitRR.setAdapter(new ArrayAdapter<ReRoll>
                (this, android.R.layout.simple_spinner_item, ReRoll.values()));
        spinnerToHitRR.setOnItemSelectedListener(this);

        /// To wound selector
        spinnerToWound = findViewById(R.id.spinnerToWound);
        spinnerToWound.setAdapter(new ArrayAdapter<D6Roll>
                (this, android.R.layout.simple_spinner_item, D6Roll.values()));
        spinnerToWound.setOnItemSelectedListener(this);
        spinnerToWound.setSelection(2);

        spinnerToWoundRR = findViewById(R.id.spinnerToWoundRR);
        spinnerToWoundRR.setAdapter(new ArrayAdapter<ReRoll>
                (this, android.R.layout.simple_spinner_item, ReRoll.values()));
        spinnerToWoundRR.setOnItemSelectedListener(this);

        /// Armor selector
        spinnerArmor = findViewById(R.id.spinnerArmor);
        spinnerArmor.setAdapter(new ArrayAdapter<SaveRoll>
                (this, android.R.layout.simple_spinner_item, SaveRoll.values()));
        spinnerArmor.setOnItemSelectedListener(this);
        spinnerArmor.setSelection(5);

        spinnerArmorRR = findViewById(R.id.spinnerArmorRR);
        spinnerArmorRR.setAdapter(new ArrayAdapter<ReRoll>
                (this, android.R.layout.simple_spinner_item, ReRoll.values()));
        spinnerArmorRR.setOnItemSelectedListener(this);

        /// Special save selector
        spinnerSpecial = findViewById(R.id.spinnerSpecial);
        spinnerSpecial.setAdapter(new ArrayAdapter<SaveRoll>
                (this, android.R.layout.simple_spinner_item, SaveRoll.values()));
        spinnerSpecial.setOnItemSelectedListener(this);
        spinnerSpecial.setSelection(5);

        spinnerSpecialRR = findViewById(R.id.spinnerSpecialRR);
        spinnerSpecialRR.setAdapter(new ArrayAdapter<ReRoll>
                (this, android.R.layout.simple_spinner_item, ReRoll.values()));
        spinnerSpecialRR.setOnItemSelectedListener(this);


        //
        switchPoison = findViewById(R.id.switchPoison);
        switchBF = findViewById(R.id.switchBF);
        switchLS = findViewById(R.id.switchLS);
        switchSpecial = findViewById(R.id.switchSpecial);

        switchPoison.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!baseInput.getText().toString().equals("")) {
                refreshHits(switchPoison);
            }
        });

        switchBF.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!baseInput.getText().toString().equals("")) {
                refreshHits(switchBF);
            }
        });

        switchLS.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!baseInput.getText().toString().equals("")) {
                refreshHits(switchLS);
            }
        });

        switchSpecial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchSpecial.isChecked()) {
                switchSpecial.setText("Fortitude");
            } else {
                switchSpecial.setText("Aegis");
            }
            if (!baseInput.getText().toString().equals("")) {
                refreshHits(switchSpecial);
            }
        });


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
                if(x1 > x2){
                Intent i = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!baseInput.getText().toString().equals("")) {
            refreshHits(adapterView);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void refreshHits(View v) {
        int attacks = Integer.parseInt(baseInput.getText().toString());
        D6Roll chanceTH = (D6Roll) spinnerToHit.getSelectedItem();
        ReRoll reRollTH = (ReRoll) spinnerToHitRR.getSelectedItem();
        boolean battleFocus = switchBF.isChecked();

        AttackRollSet arsTH = toHitRoll(attacks, chanceTH, battleFocus, reRollTH);

        if (switchPoison.isChecked()) {
            String hits = "Hits: " + arsTH.getSuccess().toString();
            String poisons = "Poisons: " + arsTH.getSixes().toString();
            ((TextView) findViewById(R.id.hitsNumber)).setText(hits);
            ((TextView) findViewById(R.id.poisonsNumber)).setText(poisons);
        } else {
            String hits = "Hits: " + arsTH.getSuccess().add(arsTH.getSixes()).toString();
            ((TextView) findViewById(R.id.hitsNumber)).setText(hits);
            ((TextView) findViewById(R.id.poisonsNumber)).setText("");
        }

        AttackRollSet arsTW;
        D6Roll chanceTW = (D6Roll) spinnerToWound.getSelectedItem();
        ReRoll reRollTW = (ReRoll) spinnerToWoundRR.getSelectedItem();
        boolean lethal = switchLS.isChecked();

        if (switchPoison.isChecked()) {
            arsTW = toWoundAfterPoison(arsTH, chanceTW, reRollTW);
        } else {
            arsTW = toWoundNoPoison(arsTH, chanceTW, reRollTW);
        }

        if (lethal) {
            String wounds = "Wounds: " + arsTW.getSuccess().toString();
            String lethals = "Lethals: " + arsTW.getSixes().toString();
            ((TextView) findViewById(R.id.woundsNumber)).setText(wounds);
            ((TextView) findViewById(R.id.lethalsNumber)).setText(lethals);
        } else {
            String wounds = "Wounds: " + arsTW.getSuccess().add(arsTW.getSixes()).toString();
            ((TextView) findViewById(R.id.woundsNumber)).setText(wounds);
            ((TextView) findViewById(R.id.lethalsNumber)).setText("");
        }

        SaveRoll armChance = (SaveRoll) spinnerArmor.getSelectedItem();
        ReRoll reRollArm = (ReRoll) spinnerArmorRR.getSelectedItem();
        WoundSet afterArmor = armourSaveRoll(arsTW, armChance, reRollArm, lethal);

        if (spinnerArmor.getSelectedItem() != SaveRoll.SevenUp) {
            String afterArmors = "Wounds after armor: " + afterArmor.getWounds().toString();
            ((TextView) findViewById(R.id.afterArmor)).setText(afterArmors);
        } else {
            ((TextView) findViewById(R.id.afterArmor)).setText("");
        }

        if (lethal) {
            String addLethals = "+ lethals: " + afterArmor.getAutoWounds().toString();
            ((TextView) findViewById(R.id.addLethals)).setText(addLethals);
        } else {
            ((TextView) findViewById(R.id.addLethals)).setText("");
        }

        SaveRoll specialChance = (SaveRoll) spinnerSpecial.getSelectedItem();
        ReRoll specialRR = (ReRoll) spinnerSpecialRR.getSelectedItem();
        Fraction afterSpecial = specialSaveRoll(afterArmor, specialChance, specialRR,
                switchSpecial.isChecked());

        String result = "After saves: " + afterSpecial.toString();
        ((TextView) findViewById(R.id.finalResult)).setText(result);
    }

    public void toCharge(View v) {
        Intent i = new Intent(this, ChargeActivity.class);
        startActivity(i);
    }
}