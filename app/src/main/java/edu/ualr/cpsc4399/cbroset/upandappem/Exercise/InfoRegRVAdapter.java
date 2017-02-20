package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseDetailActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseDetailFragment;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.R;

/**
 * Created by connorroset on 2/19/17.
 */

public class InfoRegRVAdapter extends RecyclerView.Adapter<InfoRegRVAdapter.InfoRegViewHolder>{
    public static class InfoRegViewHolder extends RecyclerView.ViewHolder{
        CardView cardview;
        TextView exercise;
        TextView date;
        TextView reps, sets;
        InfoRegViewHolder(View itemView){
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.exercise_card_view);
            exercise = (TextView) itemView.findViewById(R.id.exercise_card_title);
            date = (TextView) itemView.findViewById(R.id.exercise_card_date);
            reps = (TextView) itemView.findViewById(R.id.exercise_card_reps);
            sets = (TextView) itemView.findViewById(R.id.exercise_card_sets);
        }
    }

    private List<InfoReg> infoRegs;
    private Intent intent;
    private Context context;
    public InfoRegRVAdapter(List<InfoReg> infoRegs, Context context){
        this.infoRegs = infoRegs;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return infoRegs.size();
    }

    @Override
    public InfoRegViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_card_card_layout, viewGroup, false);

        return  new InfoRegViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final InfoRegViewHolder infoRegViewHolder, final int i) {
        infoRegViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle exerciseInfo = new Bundle();

                if (((ExerciseListActivity) v.getContext()).mTwoPane) {
                    //Bundle arguments = new Bundle();
                    //arguments.putString(ExerciseDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    ExerciseDetailFragment fragment = new ExerciseDetailFragment();
                    fragment.setArguments(exerciseInfo);
                    //FragmentManager manager = (ExerciseListActivity);

                            ((ExerciseListActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.exercise_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ExerciseDetailActivity.class);
                    intent.putExtras(exerciseInfo);
                    context.startActivity(intent);
                }
            }
        });
        infoRegViewHolder.exercise.setText(String.valueOf(infoRegs.get(i).getExerciseInfo().getExercise_name()));
        //String FormattedDate = android.text.format.DateFormat.getLongDateFormat(getApplicationContext()).format(exerciseRegimens.get(position).getDue_date().getTime());
        infoRegViewHolder.date.setText(infoRegs.get(i).getExerciseRegimen().getDue_date().toString());
        infoRegViewHolder.reps.setText((String.valueOf(infoRegs.get(i).getExerciseRegimen().getExercise_reps())));
        infoRegViewHolder.sets.setText((String.valueOf(infoRegs.get(i).getExerciseRegimen().getExercise_set())));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
