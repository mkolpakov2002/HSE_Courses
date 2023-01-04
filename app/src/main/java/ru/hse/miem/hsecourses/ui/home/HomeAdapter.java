package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ru.hse.miem.hsecourses.Constants;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Module;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Module> moduleList;
    private HomeAdapter.ItemClickListener mClickListener;
    Context context;
    Integer gravity = null;

    //0 - game
    //1 - planet
    //2 - simple card
    String adapterMode = Constants.adapterHomeSimple;


    HomeAdapter(Context context, List<Module> moduleList, String adapterMode) {
        this.context = context;
        this.moduleList = moduleList;
        this.adapterMode = adapterMode;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(Objects.equals(adapterMode, Constants.adapterHomeGame) || Objects.equals(adapterMode, Constants.adapterHomePlanet)){
            view = inflater.inflate(R.layout.recycle_view_module_item_planet, parent, false);
        } else {
            view = inflater.inflate(R.layout.recycle_view_module_item_simple, parent, false);
        }
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        if(!Objects.equals(adapterMode, Constants.adapterHomeSimple)){
            LinearLayout.LayoutParams paramsLayout = (LinearLayout.LayoutParams) holder.planetLayout.getLayoutParams();
            if(gravity == null)
                //0 element
                gravity = Gravity.CENTER_HORIZONTAL;
            else {
                if (position % 2 == 0 && gravity == Gravity.START) {
                    paramsLayout.gravity = gravity = Gravity.END;
                } else if (position % 2 == 0) {
                    paramsLayout.gravity = gravity = Gravity.START;
                } else {
                    paramsLayout.gravity = Gravity.CENTER_HORIZONTAL;
                }
            }

            holder.planetLayout.setLayoutParams(paramsLayout);
        }

        if(Objects.equals(adapterMode, Constants.adapterHomeGame)){

            holder.nameView.setText(((position + 1) + " модуль"));


            if(position == 0){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.earth));
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.earth));
                }
            } else if(position == 1){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.satellite));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.satellite));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 2){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.moon));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.moon));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 3){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.mars));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.mars));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 4) {
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.sun));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.sun));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }

            } else if(position == 5){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.moon));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.moon));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 6){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.mars));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.mars));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 7){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.phobos));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.phobos));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 8){
                if(moduleList.get(position).isUnlocked()){
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.jupiter));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.jupiter));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            } else if(position == 9) {
                if (moduleList.get(position).isUnlocked()) {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.europe));
                    holder.lockIcon.setVisibility(View.INVISIBLE);
                } else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.europe));
                    holder.lockIcon.setVisibility(View.VISIBLE);
                }
            }


        } else if(Objects.equals(adapterMode, Constants.adapterHomePlanet)) {
            holder.nameView.setText(((position + 1) + " модуль"));

            if(position == moduleList.size()-1){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir16));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir16));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir16));
                }
            } else if(position == 0){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir02));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir020));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir020));
                }
            } else if(position == 1){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir01));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir010));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir010));
                }
            } else if(position == 2){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir03));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir030));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir030));
                }
            } else if(position == 3){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir04));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir040));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir040));
                }
            } else if(position == 4) {
                if (moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir05));
                else if (!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir050));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir050));
                }

            } else if(position == 5){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir06));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir060));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir060));
                }
            } else if(position == 6){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir07));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir070));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir070));
                }
            } else if(position == 7){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir08));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir080));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir08));
                }
            } else if(position == 8){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir09));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir090));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir09));
                }
            } else if(position == 9){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir10));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir100));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir10));
                }
            } else if(position == 10){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir11));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir110));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir11));
                }
            } else if(position == 11){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir12));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir120));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir12));
                }
            } else if(position == 12){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir13));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir130));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir13));
                }
            } else if(position == 13){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir14));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir140));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir14));
                }
            } else if(position == 14){
                if(moduleList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir15));
                else if(!moduleList.get(position).isEnded() && moduleList.get(position).isUnlocked())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir150));
                else {
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir15));
                }
            }

        } else {
            holder.nameView.setText(moduleList.get(position).getModuleName());
        }
        if(!moduleList.get(position).isUnlocked() && !Objects.equals(adapterMode, Constants.adapterHomeSimple)){
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.planet.setColorFilter(filter);
        }
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //common
        TextView nameView;
        //Game/planet
        ImageView planet;
        ConstraintLayout planetLayout;
        ImageView lockIcon;
        //simple
        TextView infoView;

        ViewHolder(View view){
            super(view);
            if(Objects.equals(adapterMode, Constants.adapterHomeGame) || Objects.equals(adapterMode, Constants.adapterHomePlanet)){

                nameView = view.findViewById(R.id.textView2);
                planetLayout = view.findViewById(R.id.planetLayout);
                lockIcon = view.findViewById(R.id.imageView3);
                planet = view.findViewById(R.id.imageView2);

                ConstraintLayout planet = view.findViewById(R.id.planet);
                planet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener != null) {
                            mClickListener.onItemClick(planetLayout, getBindingAdapterPosition());
                        }
                    }
                });


            } else if(Objects.equals(adapterMode, Constants.adapterHomeSimple)){
                nameView = view.findViewById(R.id.textView5);
                ConstraintLayout parent = view.findViewById(R.id.simpleLayout);
                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener != null) {
                            mClickListener.onItemClick(planetLayout, getBindingAdapterPosition());
                        }
                    }
                });
            }

        }
    }

    // allows clicks events to be caught
    void setClickListener(HomeAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int selected);
    }

}
