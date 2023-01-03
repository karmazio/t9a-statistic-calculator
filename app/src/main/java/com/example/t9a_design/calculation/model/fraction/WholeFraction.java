package com.example.t9a_design.calculation.model.fraction;

import androidx.annotation.NonNull;

public class WholeFraction {
    private final int wholeNumber;
    private final int numerator;
    private final int denominator;

    public WholeFraction(int wholeNumber, int numerator, int denominator) {
        this.wholeNumber = wholeNumber;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public WholeFraction(Fraction fraction) {
        if (fraction.getNumerator() < fraction.getDenominator()) {
            this.wholeNumber = 0;
            this.numerator = fraction.getNumerator();
            this.denominator = fraction.getDenominator();
        } else if (fraction.getNumerator() == fraction.getDenominator()) {
            this.wholeNumber = 1;
            this.numerator = 0;
            this.denominator = 0;
        } else {
            this.wholeNumber = Math.floorDiv(fraction.getNumerator(), fraction.getDenominator());
            this.numerator = fraction.getNumerator() % fraction.getDenominator();
            this.denominator = fraction.getDenominator();
        }
    }

    @NonNull
    @Override
    public String toString() {
        if (wholeNumber == 0 && numerator == 0) {
            return "0";
        } else {
            if (wholeNumber == 0) {
                return numerator + "/" + denominator;
            } else if (numerator == 0) {
                return String.valueOf(wholeNumber);
            } else {
                return wholeNumber + " " + numerator + "/" + denominator;
            }
        }
    }
}
