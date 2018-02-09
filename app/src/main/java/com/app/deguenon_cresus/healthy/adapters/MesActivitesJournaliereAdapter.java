package com.app.deguenon_cresus.healthy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.models.Exercice;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 17/11/2017.
 */

public class MesActivitesJournaliereAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = ChoixTypeExerciceAdapter.class.getSimpleName();

    private String userId;
    private int SELF = 100;
    private static String today;

    private Context mContext;
    private ArrayList<Exercice> exerciceArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titre, time, distance, calorie, date_exercice_debut, date_exercice_fin;

        ImageView icon;

        int position;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) itemView.findViewById(R.id.icon_activite);
            titre = (TextView) itemView.findViewById(R.id.titre_activite);

            date_exercice_debut = (TextView) itemView.findViewById(R.id.date_exercice_debut);
            date_exercice_fin = (TextView) itemView.findViewById(R.id.date_exercice_fin);

            time = (TextView) itemView.findViewById(R.id.time_id);
            distance = (TextView) itemView.findViewById(R.id.distance_id);
            calorie = (TextView) itemView.findViewById(R.id.calories_id);

        }

        @Override
        public void onClick(View v) {

        }

        public void setPosiion(int position){
            this.position = position;
        }
    }


    public MesActivitesJournaliereAdapter(Context mContext, ArrayList<Exercice> exerciceArrayList) {
        this.mContext = mContext;
        this.exerciceArrayList = exerciceArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activite_journalieres_display, parent, false);

        return new MesActivitesJournaliereAdapter.ViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder) holder).setPosiion(position);

        Exercice exercice = exerciceArrayList.get(position);

        ((ViewHolder) holder).titre.setText(exercice.getTypeExercice().getNom());

        ((ViewHolder) holder).icon.setBackgroundResource(exercice.getTypeExercice().getIcon());

        ((ViewHolder) holder).date_exercice_debut.setText(exercice.getHeure_debut());

        ((ViewHolder) holder).date_exercice_fin.setText(exercice.getHeure_fin());

        ((ViewHolder) holder).time.setText(""+exercice.getTime());
        ((ViewHolder) holder).distance.setText((int)exercice.getDistance()+" m");
        ((ViewHolder) holder).calorie.setText((int)exercice.getCalories()+" cal");
    }

    @Override
    public int getItemCount() {
        return exerciceArrayList.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MesActivitesJournaliereAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MesActivitesJournaliereAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
