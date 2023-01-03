package com.example.t9a_design.calculation.model.rollStuff.charge;

import androidx.annotation.NonNull;

public enum MinMax {
    MIN("Minimised"),
    NONE("No min/max"),
    MAX("Maximised"),
    MAX_MAX("Double max.");

    final String value;

    MinMax(String value) {
        this.value = value;
    }


    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
