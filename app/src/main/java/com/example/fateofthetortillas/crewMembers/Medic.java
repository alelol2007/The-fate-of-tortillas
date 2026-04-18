package com.example.fateofthetortillas.crewMembers;

import androidx.annotation.NonNull;

import com.example.fateofthetortillas.alien.BaseEnemyMember;

import java.util.Random;

public class Medic extends BaseCrewMember {
    public BaseCrewMember member;

    public Medic(String name) {
        this(2, name, 1, 2, 0, 2, 2, false);
    }

    public Medic(int maxShield, String name, int skill, int resilience, int experience, int energy, int maxEnergy, Boolean trainingSession) {
        super(maxShield, name, skill, "Medic", resilience, experience, energy, maxEnergy, trainingSession);
    }

    @Override
    public String act(BaseCrewMember member) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance == 50) {
            member.damage(member.getEnergy());
            member.alive = Boolean.FALSE;
            return "KAPUT! " + this.name + " tried a experimental space-surgery... it went horribly wrong. " + member.name + " is now space-dust.";
        }
        int healAmount = (int) (3 * (this.skill * 1.5));
        member.heal(healAmount);

        String report = this.name + " patched up " + member.name + " for " + healAmount + " HP. 'Rub some dirt on it, you'll be fine!'";
        this.addExperience(1);
        if (this.experience >= 2) {
            this.addSkill(1);
            this.experience = 0;
            report += " | LEVEL UP: " + this.name + " actually read a medical book for once!";
        }

        return report;
    }

    public String actOther(@NonNull BaseEnemyMember enemy) {
        int dmg = (int) (0.5 * this.skill);
        enemy.damage(dmg);
        this.energy -= 1;

        return this.name + " poked the alien with a dirty needle for " + dmg + " damage. 'Ew, it's sticky!'";
    }
}