package com.example.fateofthetortillas;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fateofthetortillas.database.AppDatabase;
import com.example.fateofthetortillas.crewMembers.BaseCrewMember;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrewCardAdapter extends RecyclerView.Adapter<CrewCardAdapter.CrewViewHolder> {

    private List<BaseCrewMember> crewList;

    public CrewCardAdapter(List<BaseCrewMember> crewList) {
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        BaseCrewMember member = crewList.get(position);

        holder.txtName.setText(member.getName());
        holder.txtSpec.setText(member.getSpecialization()); // Now shows correctly
        holder.txtEnergy.setText("Energy: " + member.getEnergy() + "/" + member.getMaxEnergy());
        holder.txtSkill.setText("Skill: " + member.getSkill());
        if (member.getSpecialization() != null) {
            switch (member.getSpecialization()) {
                case "Medic":
                    holder.imgCharacter.setImageResource(R.drawable.medic);
                    break;
                case "Soldier":
                    holder.imgCharacter.setImageResource(R.drawable.soldier);
                    break;
                case "Engineer":
                    holder.imgCharacter.setImageResource(android.R.drawable.ic_menu_preferences);
                    break;
                case "Pilot":
                    holder.imgCharacter.setImageResource(android.R.drawable.ic_menu_directions);
                    break;
                case "Scientist":
                    holder.imgCharacter.setImageResource(android.R.drawable.ic_menu_search);
                    break;
            }
        }


        holder.btnFeed.setOnClickListener(v -> {
            member.setSkill(member.getSkill() + 2);
            member.setEnergy(member.getMaxEnergy());

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(v.getContext()).baseCrewMemberDao().update(member);

                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(v.getContext(), member.getName() + " ate a legendary tortilla! Stats UP!", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position);
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSpec, txtEnergy, txtSkill;
        Button btnFeed;
        android.widget.ImageView imgCharacter;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtSpec = itemView.findViewById(R.id.txtSpecialization);
            txtEnergy = itemView.findViewById(R.id.txtEnergy);
            txtSkill = itemView.findViewById(R.id.txtSkill);
            btnFeed = itemView.findViewById(R.id.btn_feed_tortilla);
            imgCharacter = itemView.findViewById(R.id.imgCharacter);
        }
    }
}
