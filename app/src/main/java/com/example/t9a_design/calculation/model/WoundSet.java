package com.example.t9a_design.calculation.model;

import com.example.t9a_design.calculation.model.fraction.Fraction;
import com.example.t9a_design.calculation.model.fraction.WholeFraction;




public class WoundSet {
    Fraction wounds;
    Fraction autoWounds;

    public WoundSet(Fraction wounds, Fraction autoWounds) {
        this.wounds = wounds;
        this.autoWounds = autoWounds;
    }

    public WoundSet() {

    }

    public Fraction getWounds() {
        return wounds;
    }

    public void setWounds(Fraction wounds) {
        this.wounds = wounds;
    }

    public Fraction getAutoWounds() {
        return autoWounds;
    }

    public void setAutoWounds(Fraction autoWounds) {
        this.autoWounds = autoWounds;
    }

    @Override
    public String toString() {
        return "Totally: " + new WholeFraction(wounds) + " wounds + " + new WholeFraction(autoWounds) + " lethals";
    }
}
