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
        BaseCrewMember currentMember = crewList.get(position);

        holder.txtName.setText(currentMember.getName() + " (" + currentMember.getSpecialization() + ")");
        holder.txtEnergy.setText("Energy: " + currentMember.getEnergy());
        holder.txtSkill.setText("Skill: " + currentMember.getSkill());


        holder.btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentEnergy = currentMember.getEnergy();

                if (currentEnergy < 100) {
                    int newEnergy = Math.min(100, currentEnergy + 25);
                    currentMember.setEnergy(newEnergy);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        AppDatabase.getInstance(v.getContext()).crewDao().update(currentMember);

                        new Handler(Looper.getMainLooper()).post(() -> {
                            notifyItemChanged(position);
                            Toast.makeText(v.getContext(), currentMember.getName() + " ate a tortilla!", Toast.LENGTH_SHORT).show();
                        });
                    });
                } else {
                    Toast.makeText(v.getContext(), currentMember.getName() + " is full!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEnergy, txtSkill;
        Button btnFeed;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEnergy = itemView.findViewById(R.id.txtEnergy);
            txtSkill = itemView.findViewById(R.id.txtSkill);
            btnFeed = itemView.findViewById(R.id.btn_feed_tortilla);
        }
    }
}