package com.example.t9a_design.calculation.model.fraction;

import androidx.annotation.NonNull;

public class Fraction {

    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public Fraction() {

    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    @NonNull
    @Override
    public String toString() {
        return numerator==0? "0" : new WholeFraction(this).toString();
    }

    public Fraction add(Fraction toAdd) {
        Fraction fraction = new Fraction();
        fraction.setNumerator((this.numerator * toAdd.denominator) + (this.denominator * toAdd.numerator));
        fraction.setDenominator(this.denominator * toAdd.denominator);
        return new Fraction(fraction.numerator, fraction.denominator);
    }

    public Fraction add(int wholeNumber) {
        return add(new Fraction(wholeNumber, 1));
    }

    public Fraction minus(Fraction toDistinct) {
        Fraction fraction = new Fraction();
        fraction.setNumerator((this.numerator * toDistinct.denominator) - (this.denominator * toDistinct.numerator));
        fraction.setDenominator(this.denominator * toDistinct.denominator);
        return new Fraction(fraction.numerator, fraction.denominator);
    }

    public Fraction minus(int wholeNumber) {
        return minus(new Fraction(wholeNumber, 1));
    }

    public Fraction multiply(Fraction fraction) {
        return new Fraction(this.numerator * fraction.numerator,
                this.denominator * fraction.denominator);
    }

    public Fraction multiply(int wholeNumber) {
        return multiply(new Fraction(wholeNumber, 1));
    }

    public Fraction divide(Fraction fraction) {
        return new Fraction(this.numerator * fraction.denominator,
                this.denominator * fraction.numerator);
    }

    public Fraction divide(int wholeNumber) {
        return divide(new Fraction(wholeNumber, 1));
    }


}
