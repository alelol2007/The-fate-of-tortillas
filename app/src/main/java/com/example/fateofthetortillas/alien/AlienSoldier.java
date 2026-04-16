package com.example.fateofthetortillas.alien;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;

public class AlienSoldier extends BaseEnemyMember{
    public AlienSoldier(int skill, float possibility){
        this(
                3,skill, (int)(skill*2), (int)(skill*2.2),true, (int)(skill*2.5), (int)(skill*2.7), (float) (possibility*0.7)
        );
    }
    public AlienSoldier(int type, int skill, int energy, int maxEnergy, Boolean alive, int maxShield, int resilience, float possibility){
        super( type,  skill, energy, maxEnergy, alive, maxShield, resilience,(float) (possibility));
    }
    public String act(BaseEnemyMember member){
        member.heal((int)((skill/10)));
        return "my art is definitely not healing";
    }
    public String actOther(BaseCrewMember crew) {
        crew.damage((int)(0.8*this.skill));
        this.energy -=1;
        return "for the world of platapussss";
    }
}
