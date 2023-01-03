package com.example.t9a_design.calculation.model.rollStuff.charge;

import androidx.annotation.NonNull;

public enum ChargeReRoll {
    NONE("No RR"),
    ONES("RR 1s"),
    FAILED("Failed");

    final String value;

    ChargeReRoll(String value) {
        this.value = value;
    }


    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
