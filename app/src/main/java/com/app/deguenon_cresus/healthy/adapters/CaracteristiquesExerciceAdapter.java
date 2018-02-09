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
import com.app.deguenon_cresus.healthy.models.CarateristiqueExercice;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 17/11/2017.
 */

public class CaracteristiquesExerciceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = ChoixTypeExerciceAdapter.class.getSimpleName();

    private String userId;
    private int SELF = 100;
    private static String today;

    private Context mContext;
    private ArrayList<CarateristiqueExercice> carateristiqueExerciceArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView valeur;

        ImageView icon;

        int position;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) itemView.findViewById(R.id.icon_caracterisque);
            valeur = (TextView) itemView.findViewById(R.id.valeur_carateristique);
        }

        @Override
        public void onClick(View v) {

        }

        public void setPosiion(int position){
            this.position = position;
        }
    }


    public CaracteristiquesExerciceAdapter(Context mContext, ArrayList<CarateristiqueExercice> carateristiqueExerciceArrayList) {
        this.mContext = mContext;
        this.carateristiqueExerciceArrayList = carateristiqueExerciceArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caracteristiques_exercice_display, parent, false);

        return new CaracteristiquesExerciceAdapter.ViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        CarateristiqueExercice carateristiqueExercice = carateristiqueExerciceArrayList.get(position);

        ((CaracteristiquesExerciceAdapter.ViewHolder) holder).valeur.setText(carateristiqueExercice.getValeur());

        ((CaracteristiquesExerciceAdapter.ViewHolder) holder).icon.setBackgroundResource(carateristiqueExercice.getIcon());
    }

    @Override
    public int getItemCount() {
        return carateristiqueExerciceArrayList.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private CaracteristiquesExerciceAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CaracteristiquesExerciceAdapter.ClickListener clickListener) {
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
