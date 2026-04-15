package com.example.fateofthetortillas.model;


import java.util.Random;

public class Engineer extends BaseCrewMember{
    public BaseCrewMember member;
    public Engineer(String name){
        this(4, name, 1, 4, 0, 3, 3, false);
    }
    public Engineer(int maxShield,String name, int skill,  int resilience,  int experience, int energy, int maxEnergy, Boolean trainingSession){
        super(maxShield, name, skill, "Engineer", resilience,  experience, energy, maxEnergy, trainingSession);

    }

    @Override
    public String act(BaseCrewMember target) {
        Random rand = new Random();
        int chance = rand.nextInt(20);

        if (chance == 0) {
            target.damage(2);
            return "CLANG! " + this.name + " hit " + target.name + "'s armor with a hammer to 'fix' a dent. It didn't work. " + target.name + " looks annoyed.";
        }

        int shieldBoost = this.skill + 2;
        target.maxShield += 1;

        String report = this.name + " applied 'Tactical Duct Tape' to " + target.name + ". Shields boosted by " + shieldBoost + "!";


        this.addExperience(1);
        if (this.experience >= 3) {
            this.addSkill(1);
            this.experience = 0;
            report += " | LEVEL UP: " + this.name + " figured out which end of the screwdriver to hold!";
        }

        return report;
    }

    @Override
    public String actOther(BaseEnemyMember enemy) {
        int dmg = this.skill + 3;
        enemy.damage(dmg);
        this.energy -= 1;

        String[] insults = {
                "I threw a heavy-duty wrench at the alien! +10 blunt force trauma.",
                "I redirected the microwave radiation... it's popcorn time!",
                "I hit it with a soldering iron. It smells like burnt space-calamari now."
        };

        String randomInsult = insults[new Random().nextInt(insults.length)];
        return this.name + " used 'Creative Engineering'! " + randomInsult + " (" + dmg + " damage)";
    }
}
