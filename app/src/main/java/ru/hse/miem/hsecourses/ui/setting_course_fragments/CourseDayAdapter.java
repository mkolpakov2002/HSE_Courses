package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.stream.Collectors;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Task;

public class CourseDayAdapter extends RecyclerView.Adapter<CourseDayAdapter.ViewHolder>{

    private List<Day> dayList;
    private List<Task> taskList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    CourseDayAdapter(Context context, List<Day> data, List<Task> taskList) {
        this.mInflater = LayoutInflater.from(context);
        this.dayList = data;
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public CourseDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_day_item, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull CourseDayAdapter.ViewHolder holder, int position) {
        String day = dayList.get(position).getDayName();
        holder.dayName.setText(day);
        if(taskList.stream().anyMatch(t -> t.getDayNumber() == position)){
            holder.isReminderSaved.setText(context.getString(R.string.status_days_tasks_saved));
            holder.imageViewCheckMarkReminderSaved.setImageResource(R.drawable.ic_baseline_alarm_on_24);
            holder.parentView.setCardElevation(8);
            holder.parentView.getBackground().setAlpha(255);
        } else {
            holder.isReminderSaved.setText(context.getString(R.string.status_days_tasks_not_saved));
            holder.imageViewCheckMarkReminderSaved.setImageResource(R.drawable.ic_baseline_alarm_add_24);
            holder.parentView.setCardElevation(0);
            holder.parentView.getBackground().setAlpha(200);
        }
    }

    public void updateData(List<Day> data, List<Task> taskList){
        dayList = data;
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dayList.size();
    }

    // convenience method for getting data at click position
    Day getItem(int id) {
        return dayList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayName;
        TextView isReminderSaved;
        MaterialButton selectTime;
        MaterialCardView parentView;
        ImageView addReminderIcon;
        ImageView imageViewCheckMarkReminderSaved;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewCheckMarkReminderSaved = itemView.findViewById(R.id.imageViewAddReminder);
            parentView = itemView.findViewById(R.id.cardView);
            parentView.setCheckable(true);
            parentView.setChecked(false);
            isReminderSaved = itemView.findViewById(R.id.textViewIsReminderSaved);
            dayName = itemView.findViewById(R.id.textViewReminderName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(parentView, getBindingAdapterPosition());
                    }
                }
            });
        }

    }
}
