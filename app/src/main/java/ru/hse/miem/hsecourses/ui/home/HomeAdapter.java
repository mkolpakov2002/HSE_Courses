package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.courses.Week;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Week> weekList;
    private HomeAdapter.ItemClickListener mClickListener;
    Context context;

    boolean isGameAdapter = false;

    boolean lineModeToRight = true;

    HomeAdapter(Context context, List<Week> courseList) {
        this.context = context;
        this.weekList = courseList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycle_view_week_item, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        if(isGameAdapter){

        } else {
            holder.nameView.setText(((position + 1) + " неделя"));
            //LinearLayout

            //ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(position%3==0){
                ConstraintLayout constraintLayout = holder.weekLayout;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(holder.planet.getId(),ConstraintSet.START,holder.weekLayout.getId(),ConstraintSet.START,0);
                constraintSet.applyTo(constraintLayout);
            } else if(position%3==1){
                ConstraintLayout constraintLayout = holder.weekLayout;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(holder.planet.getId(),ConstraintSet.START,holder.weekLayout.getId(),ConstraintSet.START,0);
                constraintSet.connect(holder.planet.getId(),ConstraintSet.END,holder.weekLayout.getId(),ConstraintSet.END,0);
                constraintSet.applyTo(constraintLayout);
            } else {
                ConstraintLayout constraintLayout = holder.weekLayout;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(holder.planet.getId(),ConstraintSet.END,holder.weekLayout.getId(),ConstraintSet.END,0);
                constraintSet.applyTo(constraintLayout);
            }
            //holder.weekLayout.setLayoutParams(params);
            if(position == 0){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir01));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir010));
            } else if(position == 1){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir02));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir020));
            } else if(position == 2){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir03));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir030));
            } else if(position == 3){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir04));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir040));
            } else if(position == 4){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir05));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir050));
            } else if(position == 5){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir06));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir060));
            } else if(position == 6){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir07));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir070));
            } else if(position == 7){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir08));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir080));
            } else if(position == 8){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir09));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir090));
            } else if(position == 9){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir10));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir100));
            } else if(position == 10){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir11));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir110));
            } else if(position == 11){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir12));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir120));
            } else if(position == 12){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir13));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir130));
            } else if(position == 13){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir14));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir140));
            } else if(position == 14){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir15));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir150));
            } else if(position == 15){
                if(weekList.get(position).isEnded())
                    holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir16));
                else holder.planet.setImageDrawable(context.getDrawable(R.drawable.cir16));
            }
        }
    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        ImageView planet;
        ConstraintLayout weekLayout;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.textView2);
            weekLayout = view.findViewById(R.id.weekLayout);
            planet = view.findViewById(R.id.imageView2);
            weekLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        //mClickListener.onItemClick(parentView, weekList.get(getBindingAdapterPosition()).getCourseId());
                    }
                }
            });
        }
    }

    // allows clicks events to be caught
    void setClickListener(HomeAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int selectedId);
    }

}
