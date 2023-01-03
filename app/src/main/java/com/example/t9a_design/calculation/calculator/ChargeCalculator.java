package com.example.t9a_design.calculation.calculator;

import com.example.t9a_design.calculation.model.fraction.Fraction;
import com.example.t9a_design.calculation.model.fraction.WholeFraction;
import com.example.t9a_design.calculation.model.rollStuff.charge.ChargeReRoll;
import com.example.t9a_design.calculation.model.rollStuff.charge.FourD6;
import com.example.t9a_design.calculation.model.rollStuff.charge.MinMax;

import java.util.ArrayList;
import java.util.List;

public class ChargeCalculator {

    public static float chargeRoll(int rollNeeded, MinMax minMax, ChargeReRoll reRoll) {
        List<FourD6> allRolls = getAll4D6Rolls(minMax);

        if (reRoll == ChargeReRoll.ONES) {
            allRolls = reRollOnes(allRolls);
        }

        if (minMax == MinMax.MIN) {
            removeBiggest(allRolls);
        }

        float success = 0;
        float all = 0;
        for (FourD6 roll : allRolls) {
            if (roll.getA() + roll.getB() >= rollNeeded ||
                    roll.getA() + roll.getC() >= rollNeeded ||
                    roll.getA() + roll.getD() >= rollNeeded ||
                    roll.getB() + roll.getC() >= rollNeeded ||
                    roll.getB() + roll.getD() >= rollNeeded ||
                    roll.getC() + roll.getD() >= rollNeeded) {
                success++;
            }
            all++;
        }

        float result = success / all;

        if (reRoll == ChargeReRoll.FAILED) {
            result = result + (1 - result) * result;
        }

        return result;
    }

    private static void removeBiggest(List<FourD6> allRolls) {
        for (FourD6 roll : allRolls) {
            int temp = Math.max(roll.getA(), roll.getB());
            int max = Math.max(temp, roll.getC());
            if (roll.getA() == max) {
                roll.setA(0);
            } else if (roll.getB() == max) {
                roll.setB(0);
            } else  {
                roll.setC(0);
            }
        }
    }

    private static List<FourD6> reRollOnes (List<FourD6> allRolls) {
        List <FourD6> result = new ArrayList<>();
        for (FourD6 roll : allRolls) {
            result.add(roll);
            if (roll.getA() == 1) {
                for (int i = 2; i <= 6; i++) {
                    result.add(new FourD6(i, roll.getB(), roll.getC(), roll.getD()));
                }
            }
            if (roll.getB() == 1) {
                for (int i = 2; i <= 6; i++) {
                    result.add(new FourD6(roll.getA(), i, roll.getC(), roll.getD()));
                }
            }
            if (roll.getC() == 1) {
                for (int i = 2; i <= 6; i++) {
                    result.add(new FourD6(roll.getA(), roll.getB(), i, roll.getD()));
                }
            }
            if (roll.getD() == 1) {
                for (int i = 2; i <= 6; i++) {
                    result.add(new FourD6(roll.getA(), roll.getB(), roll.getC(), i));
                }
            }

        }
        return result;
    }

    private static List<FourD6> getAll4D6Rolls(MinMax minMax) {
        List<FourD6> allRolls = new ArrayList<>();
        if (minMax == MinMax.NONE) {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    FourD6 roll = new FourD6(i, j, 0, 0);
                    allRolls.add(roll);
                }
            }
        }
        if (minMax == MinMax.MIN || minMax == MinMax.MAX) {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    for (int k = 1; k <= 6; k++) {
                        FourD6 roll = new FourD6(i, j, k, 0);
                        allRolls.add(roll);
                    }
                }
            }
        }
        if (minMax == MinMax.MAX_MAX) {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    for (int k = 1; k <= 6; k++) {
                        for (int l = 1; l <= 6; l++) {
                            FourD6 roll = new FourD6(i, j, k, l);
                            allRolls.add(roll);
                        }
                    }
                }
            }
        }
        return allRolls;
    }
}
