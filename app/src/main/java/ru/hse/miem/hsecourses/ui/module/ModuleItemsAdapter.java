package ru.hse.miem.hsecourses.ui.module;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Topic;

public class ModuleItemsAdapter extends RecyclerView.Adapter<ModuleItemsAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Topic> topicList;
    private ModuleItemsAdapter.ItemClickListener mClickListener;
    Context context;
    Integer gravity = null;


    ModuleItemsAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
        this.inflater = LayoutInflater.from(context);
        Log.e("adbearrrrrr", String.valueOf(topicList.size()));
    }

    @NonNull
    @Override
    public ModuleItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = inflater.inflate(R.layout.recycle_view_module_item_full, parent, false);

        return new ModuleItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModuleItemsAdapter.ViewHolder holder, int position) {
        holder.nameView.setText(topicList.get(position).getTopicName());
        if(topicList.get(position).getItemType()==0){
            holder.itemTypeIconButton.setIcon(AppCompatResources.getDrawable(context,
                    R.drawable.ic_learning_theme));
        } else if(topicList.get(position).getItemType()==1){
            holder.itemTypeIconButton.setIcon(AppCompatResources.getDrawable(context,
                    R.drawable.ic_book));
        } else if(topicList.get(position).getItemType()==2){
            holder.itemTypeIconButton.setIcon(AppCompatResources.getDrawable(context,
                    R.drawable.ic_checkered_flags));
        } else if(topicList.get(position).getItemType()==3){
            holder.itemTypeIconButton.setIcon(AppCompatResources.getDrawable(context,
                    R.drawable.ic_checkered_flags));
        }

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //common
        TextView nameView;

        MaterialButton itemTypeIconButton;
        ConstraintLayout planetLayout;
        TextView infoView;

        ViewHolder(View view){
            super(view);

            nameView = view.findViewById(R.id.textView5);
            ConstraintLayout parent = view.findViewById(R.id.simpleLayout);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(planetLayout, topicList.get(getBindingAdapterPosition()));
                    }
                }
            });
            itemTypeIconButton = view.findViewById(R.id.buttonMinus);
            itemTypeIconButton.setClickable(false);
        }
    }

    // allows clicks events to be caught
    void setClickListener(ModuleItemsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Topic selected);
    }

}
