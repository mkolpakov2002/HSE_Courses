package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Course;

public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private List<Course> coursesList;
    private SelectCourseAdapter.ItemClickListener mClickListener;
    Context context;
    private int lastCheckedPosition = -1;
    Course selectedCourse;

    SelectCourseAdapter(Context context, List<Course> courseList, Course selected) {
        this.context = context;
        this.coursesList = courseList;
        this.inflater = LayoutInflater.from(context);
        this.selectedCourse = selected;
    }

    public void setData(List<Course> courseList){
        this.coursesList = courseList;
        notifyDataSetChanged();
    }

    @Override
    public SelectCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycle_view_course_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectCourseAdapter.ViewHolder holder, int position) {
        String name = coursesList.get(position).getCourseName();
        holder.nameView.setText(name);
        holder.parentView.setCardElevation(0);
        holder.parentView.setStrokeColor(Color.BLACK);
        holder.parentView.getBackground().setAlpha(200);
        holder.imageViewCourseSelected.setVisibility(View.INVISIBLE);
        //holder.parentView.setStrokeWidth(3);
        if(selectedCourse!=null && lastCheckedPosition==-1){
            if(selectedCourse.getCourseName().equals(name)){
                lastCheckedPosition = holder.getBindingAdapterPosition();
                holder.parentView.setCardElevation(8);
                //parentView.setStrokeWidth(8);
                holder.parentView.setStrokeColor(Color.BLUE);
                holder.parentView.getBackground().setAlpha(255);
                holder.imageViewCourseSelected.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        MaterialCardView parentView;
        ImageView imageViewCourseSelected;
        ViewHolder(View view){
            super(view);
            imageViewCourseSelected = view.findViewById(R.id.imageViewCourseSelected);
            nameView = view.findViewById(R.id.name);
            parentView = view.findViewById(R.id.parent_view);
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        int copyOfLastCheckedPosition = lastCheckedPosition;
                        lastCheckedPosition = getBindingAdapterPosition();
                        parentView.setCardElevation(8);
                        //parentView.setStrokeWidth(8);
                        parentView.setStrokeColor(Color.BLUE);
                        parentView.getBackground().setAlpha(255);
                        imageViewCourseSelected.setVisibility(View.VISIBLE);
                        if(copyOfLastCheckedPosition!=lastCheckedPosition)
                            notifyItemChanged(copyOfLastCheckedPosition);
                        mClickListener.onItemClick(parentView, coursesList.get(getBindingAdapterPosition()).getCourseId());
                    }
                }
            });
        }
    }

    // allows clicks events to be caught
    void setClickListener(SelectCourseAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int selectedId);
    }

    void setSelected(View view){

    }
}
