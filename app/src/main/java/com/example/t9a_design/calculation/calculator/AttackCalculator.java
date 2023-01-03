package com.example.t9a_design.calculation.calculator;

import com.example.t9a_design.calculation.model.AttackRollSet;
import com.example.t9a_design.calculation.model.WoundSet;
import com.example.t9a_design.calculation.model.fraction.Fraction;
import com.example.t9a_design.calculation.model.rollStuff.singleRoll.D6Roll;
import com.example.t9a_design.calculation.model.rollStuff.ReRoll;
import com.example.t9a_design.calculation.model.rollStuff.singleRoll.SaveRoll;


public class AttackCalculator {

    public static AttackRollSet roll(Fraction diceRolled, Fraction poisons, D6Roll chance, boolean battleFocus,
                                     ReRoll reRoll) {
        AttackRollSet result = basicRoll(diceRolled, chance);

        switch (reRoll) {
            case NONE: {
                break;
            }
            case ONES: {
                AttackRollSet added = basicRoll(result.getSixes(), chance);
                result.setSuccess(result.getSuccess().add(added.getSuccess()));
                result.setSixes(result.getSixes().add(added.getSixes()));
                break;
            }
            case SIXES: {
                AttackRollSet added = basicRoll(result.getSixes(), chance);
                if (result.getSuccess().getNumerator() != 0) {
                    result.setSuccess(result.getSuccess().add(added.getSuccess()));
                }
                result.setSixes(added.getSixes());
                break;
            }
            case FAILED: {
                AttackRollSet added = basicRoll(result.getFailed(), chance);
                result.setSuccess(result.getSuccess().add(added.getSuccess()));
                result.setSixes(result.getSixes().add(added.getSixes()));
                break;
            }
            case SUCCESS: {
                AttackRollSet added = basicRoll(result.getSixes().add(result.getSuccess()), chance);
                result = added;
                break;
            }
        }

        if (battleFocus) {
            result.setSuccess(result.getSuccess().add(result.getSixes()));
        }

        result.setSuccess(result.getSuccess().add(poisons));
        return result;
    }

    public static AttackRollSet basicRoll(Fraction diceRolled, D6Roll chance) {
        AttackRollSet result = new AttackRollSet();
        result.setSuccess(diceRolled.multiply(chance.getChance().minus(new Fraction(1, 6))));
        result.setSixes(diceRolled.multiply(new Fraction(1, 6)));
        result.setFailed(diceRolled.multiply(chance.getFailed()));
        return result;
    }

    public static AttackRollSet basicRoll(Fraction diceRolled, SaveRoll chance) {
        AttackRollSet result = new AttackRollSet();
        result.setSuccess(diceRolled.multiply(chance.getChance().minus(new Fraction(1, 6))));
        result.setSixes(diceRolled.multiply(new Fraction(1, 6)));
        result.setFailed(diceRolled.multiply(chance.getFailed()));
        return result;
    }



    public static AttackRollSet toHitRoll(int numberOfDice, D6Roll chance, boolean battleFocus,
                                          ReRoll reRoll) {

        System.out.println("Diced rolled: " + numberOfDice);
        System.out.println("Chance: " + chance);
        System.out.println(battleFocus ? "with battleFocus" : "----");
        switch (reRoll) {
            case NONE:
                System.out.println("no rr");
                break;
            case ONES:
                System.out.println("rr ones");
                break;
            case SIXES:
                System.out.println("rr sixes");
                break;
            case FAILED:
                System.out.println("rr failed");
                break;
            case SUCCESS:
                System.out.println("rr success");
                break;

        }
        return roll(new Fraction(numberOfDice, 1), new Fraction(0, 1), chance,
                battleFocus, reRoll);
    }

    public static AttackRollSet toWoundAfterPoison(AttackRollSet afterHit, D6Roll chance, ReRoll reRoll) {
        return roll(afterHit.getSuccess(), afterHit.getSixes(), chance, false, reRoll);
    }

    public static AttackRollSet toWoundNoPoison(AttackRollSet afterHit, D6Roll chance, ReRoll reRoll) {
        return roll(afterHit.getSuccess().add(afterHit.getSixes()), new Fraction(0, 1), chance,
                false, reRoll);
    }

    public static WoundSet armourSaveRoll(AttackRollSet wounds, SaveRoll chance, ReRoll reRoll, boolean lethals) {


        if (chance == SaveRoll.SevenUp) {
            return lethals ? new WoundSet(wounds.getSuccess(), wounds.getSixes())
                    : new WoundSet(wounds.getSuccess().add(wounds.getSixes()), new Fraction(0, 1));
        }

        AttackRollSet roll;
        WoundSet afterArmour = new WoundSet();

        if (lethals) {
            afterArmour.setAutoWounds(wounds.getSixes());
            roll = basicRoll(wounds.getSuccess(), chance);
        } else {
            afterArmour.setAutoWounds(new Fraction(0, 1));
            roll = basicRoll(wounds.getSuccess().add(wounds.getSixes()), chance);
        }

        reRollSave(chance, reRoll, roll);
        afterArmour.setWounds(roll.getFailed());

        return afterArmour;
    }

    public static Fraction specialSaveRoll(WoundSet wounds, SaveRoll chance, ReRoll reRoll, boolean ignoredByLethals) {
        if (chance == SaveRoll.SevenUp) {
            return wounds.getWounds().add(wounds.getAutoWounds());
        }

        Fraction autoWounds;
        if (ignoredByLethals) {
            autoWounds = wounds.getAutoWounds();
        } else {
            autoWounds = new Fraction(0, 1);
            wounds.setWounds(wounds.getWounds().add(wounds.getAutoWounds()));
        }

        AttackRollSet roll = basicRoll(wounds.getWounds(), chance);

        reRollSave(chance, reRoll, roll);

        return roll.getFailed().add(autoWounds);
    }

    private static void reRollSave(SaveRoll chance, ReRoll reRoll, AttackRollSet roll) {
        switch (reRoll) {
            case NONE: {
                break;
            }
            case ONES: {
                AttackRollSet modify = basicRoll(roll.getSixes(), chance);
                roll.setFailed(roll.getFailed().minus(modify.getSuccess()).minus(modify.getSixes()));
                break;
            }
            case SIXES: {
                AttackRollSet modify = basicRoll(roll.getSixes(), chance);
                roll.setFailed(roll.getFailed().add(modify.getFailed()));
                break;
            }
            case FAILED: {
                AttackRollSet modify = basicRoll(roll.getFailed(), chance);
                roll.setFailed(roll.getFailed().minus(modify.getSuccess()).minus(modify.getSixes()));
                break;
            }
            case SUCCESS: {
                AttackRollSet modify = basicRoll(roll.getSuccess().add(roll.getSixes()), chance);
                roll.setFailed(roll.getFailed().add(modify.getFailed()));
                break;
            }
        }
    }
}
