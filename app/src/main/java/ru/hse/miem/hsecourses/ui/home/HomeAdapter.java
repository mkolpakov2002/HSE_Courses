package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Task> weekList;
    private HomeAdapter.ItemClickListener mClickListener;
    Context context;

    HomeAdapter(Context context, List<Task> courseList) {
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
        holder.nameView.setText(position + 1 + " ");
        holder.parentView.getBackground().setAlpha(200);
    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        MaterialCardView parentView;
        ImageView checkMark;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.textView2);
            checkMark = view.findViewById(R.id.imageView3);
            parentView = view.findViewById(R.id.parent_view);
            parentView.setOnClickListener(new View.OnClickListener() {
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

    void setSelected(View view){

    }
}
