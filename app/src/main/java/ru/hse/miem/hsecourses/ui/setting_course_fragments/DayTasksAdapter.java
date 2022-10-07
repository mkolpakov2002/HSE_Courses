package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;

public class DayTasksAdapter extends RecyclerView.Adapter<DayTasksAdapter.ViewHolder>{

    private List<Task> mData;
    Context context;
    private LayoutInflater mInflater;

    onDeleteClickedListener mCallback;

    DayTasksAdapter(Context context, List<Task> mData){
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_time_item, parent, false);
        return new DayTasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String time = mData.get(position).getInterval();
        holder.workingTimeName.setText(time);
    }

    // convenience method for getting data at click position
    Task getItem(int id) {
        return mData.get(id);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workingTimeName;
        MaterialButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            workingTimeName = itemView.findViewById(R.id.timeName);
            deleteButton = itemView.findViewById(R.id.buttonDeleteTime);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(getBindingAdapterPosition());
                    if (mCallback != null) {
                        mCallback.deleteClicked();
                    }
                }
            });
        }
    }

    public void setOnDeleteClickedListener(onDeleteClickedListener mCallback) {
        this.mCallback = mCallback;
    }

    public interface onDeleteClickedListener {
        void deleteClicked();
    }

}
