package com.example.fateofthetortillas.alien;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;

public class AlienFaithKiller extends BaseEnemyMember{
    public AlienFaithKiller(int skill, float possibility){
        this(
                2,skill, (int)(skill*1.5), (int)(skill*1.7),true, (int)(skill*1.5), (int)(skill*1.7), (float) (possibility*0.1)
        );
    }
    public AlienFaithKiller(int type, int skill, int energy, int maxEnergy, Boolean alive, int maxShield, int resilience, float possibility){
        super( type,  skill, energy, maxEnergy, alive, maxShield, resilience,(float) (possibility));
    }
    @Override
    public String act(BaseEnemyMember member){
        member.resilience += ((int)(3*(skill/10)));
        return "I am the one, and you are the only one, riseeeee"+ member.name;
    }
    public String actOther(BaseCrewMember crew) {
        crew.damage((int)(0.5*this.skill));
        this.energy -=1;
        return "dieee heretics";
    }
    public String faithKiller(Crew crew) {
        crew.looseFaith(1);

        if (crew.getFaith() <= 0) {
            this.alive = false;
            return "THE END IS NIGH! You are heretics of an unknown land; your lack of faith has destroyed you.";
        }

        return "The " + this.name + " whispers lies into your ears. Your crew's faith wavers... (-1 Faith or was it moreeeeeee)";
    }

}
