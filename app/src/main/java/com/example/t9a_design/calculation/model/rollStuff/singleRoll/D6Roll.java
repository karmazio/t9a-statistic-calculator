package com.example.t9a_design.calculation.model.rollStuff.singleRoll;

import androidx.annotation.NonNull;

import com.example.t9a_design.calculation.model.fraction.Fraction;


public enum D6Roll {
    TwoUp(new Fraction(5, 6), "2+"),
    ThreeUp(new Fraction(4, 6), "3+"),
    FourUp(new Fraction(3, 6), "4+"),
    FiveUp(new Fraction(2, 6), "5+"),
    SixUp(new Fraction(1, 6), "6+");

    final Fraction chance;
    final String value;

    public Fraction getChance() {
        return chance;
    }

    D6Roll(Fraction chance, String value) {
        this.chance = chance;
        this.value = value;
    }

    public Fraction getFailed() {
        Fraction hundred = new Fraction(1, 1);
        return hundred.minus(this.chance);
    }


    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
