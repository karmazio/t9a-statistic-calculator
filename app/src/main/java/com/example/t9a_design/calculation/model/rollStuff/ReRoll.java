package com.example.t9a_design.calculation.model.rollStuff;

import androidx.annotation.NonNull;

public enum ReRoll {
    NONE("No RR"),
    ONES("RR 1s"),
    SIXES("RR 6s"),
    FAILED("Failed"),
    SUCCESS("Success");

    final String value;

    ReRoll(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
