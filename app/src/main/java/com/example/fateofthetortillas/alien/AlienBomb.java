package com.example.fateofthetortillas.alien;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;

public class AlienBomb extends BaseEnemyMember {

    public AlienBomb(int skill, float possibility){
        this(
                1,skill, (int)(skill*1.5), (int)(skill*1.7),true, (int)(skill*1.5), (int)(skill*1.7), (float) (possibility*0.5)
        );
    }
    public AlienBomb(int type, int skill, int energy, int maxEnergy, Boolean alive, int maxShield, int resilience, float possibility){
        super( type,  skill, energy, maxEnergy, alive, maxShield, resilience,(float) (possibility));
    }

    public String act(BaseEnemyMember member){

        member.heal((int)(3*(skill/10)));
        return "kaputttt^-1?";
    }
    public String actOther(BaseCrewMember crew){
        //you have to write a loop for this one
        crew.damage((int)(0.5*this.skill));
        this.energy -=1;
        return "kaputttt^1";
    }
    public String explosion(Crew crew){
        int dmg = (int)(this.skill *1.2);
        for (BaseCrewMember member : crew.crew){
            member.damage(dmg);
        }
        this.energy =0;
        this.alive=false;
        return "BOOM! " + this.name + " couldn't hold it in anymore and exploded! Everyone took " + dmg + " damage. " + this.name + " is now just a stain on the wall.";    }

    //this one will explode to the whole team or that is its action to others
}
