package com.developer.grebnev.buildingprojectmodels.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.grebnev.buildingprojectmodels.DetailsProjectActivity;
import com.developer.grebnev.buildingprojectmodels.R;
import com.developer.grebnev.buildingprojectmodels.dialog.EditingStageDialog;
import com.developer.grebnev.buildingprojectmodels.model.StageList;
import com.developer.grebnev.buildingprojectmodels.model.StagesProject;

import java.util.List;

/**
 * Created by Grebnev on 08.05.2017.
 */

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {

    List<StagesProject> stagesProject;
    FragmentManager fragmentManager;

    public StageAdapter(List<StagesProject> stagesProject, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.stagesProject = stagesProject;
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {
        TextView nameStage;
        TextView responsibleStage;
        TextView durationStage;
        TextView costStage;
        public StageViewHolder(View itemView) {
            super(itemView);
            nameStage = (TextView) itemView.findViewById(R.id.tv_name);
            responsibleStage = (TextView) itemView.findViewById(R.id.tv_stage_responsible);
            durationStage = (TextView) itemView.findViewById(R.id.tv_stage_duration);
            costStage = (TextView) itemView.findViewById(R.id.tv_stage_cost);
        }
    }

    @Override
    public StageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_stage_item, parent, false);
        StageViewHolder stageViewHolder = new StageViewHolder(view);
        return stageViewHolder;
    }

    @Override
    public void onBindViewHolder(final StageViewHolder holder, final int position) {
        holder.nameStage.setText(stagesProject.get(position).nameStageProject);
        holder.responsibleStage.setText(stagesProject.get(position).responsibleForStageProject);
        holder.durationStage.setText(Integer.toString(stagesProject.get(position).durationStageProject));
        holder.costStage.setText(Integer.toString(stagesProject.get(position).costStageProject));

        final View itemView = holder.itemView;

        itemView.setVisibility(View.VISIBLE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment editingStageDialog = EditingStageDialog.newInstance(stagesProject.get(position));
                editingStageDialog.show(fragmentManager, "EditStageDialog");
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StageList.deleteStage(stagesProject.get(position).getId(), stagesProject.get(position));
                if (v.getContext() instanceof DetailsProjectActivity) {
                    ViewPager viewPager = (ViewPager) ((DetailsProjectActivity)v.getContext()).findViewById(R.id.view_pager);
                    ((DetailsProjectActivity)v.getContext()).setupViewPager(viewPager);
                }
                return true;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return stagesProject.size();
    }
}
