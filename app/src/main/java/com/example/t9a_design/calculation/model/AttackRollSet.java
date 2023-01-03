package com.example.t9a_design.calculation.model;

import com.example.t9a_design.calculation.model.fraction.Fraction;
import com.example.t9a_design.calculation.model.fraction.WholeFraction;


public class AttackRollSet {

    Fraction success;
    Fraction sixes;
    Fraction failed;

    public AttackRollSet(Fraction success, Fraction sixes, Fraction failed) {
        this.success = success;
        this.sixes = sixes;
        this.failed = failed;
    }

    public AttackRollSet() {

    }

    public Fraction getSuccess() {
        return success;
    }

    public void setSuccess(Fraction success) {
        this.success = success;
    }

    public Fraction getSixes() {
        return sixes;
    }

    public void setSixes(Fraction sixes) {
        this.sixes = sixes;
    }

    public Fraction getFailed() {
        return failed;
    }

    public void setFailed(Fraction failed) {
        this.failed = failed;
    }

    @Override
    public String toString() {
        return "AttackRollSet: " +
                "\nsuccess= " + new WholeFraction(success) +
                "\nsixes= " + new WholeFraction(sixes) +
                "\nfailed= " + new WholeFraction(failed) +"\n";
    }
}
