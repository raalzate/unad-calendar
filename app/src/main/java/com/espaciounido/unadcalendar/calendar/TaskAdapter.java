package com.espaciounido.unadcalendar.calendar;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.calendar.domain.Task;

import java.util.List;

/**
 * Created by MyMac on 2/10/16.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnChangeListener onChangeListener;
    private final TypeRender typeRender;

    public TaskAdapter(List<Task> taskList, OnChangeListener onChangeListener) {
        this.taskList = taskList;
        this.onChangeListener = onChangeListener;
        this.typeRender = TypeRender.WITH_ACTION;
    }

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
        this.onChangeListener = null;
        this.typeRender = TypeRender.WITHOUT_ACTION;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (typeRender.equals(TypeRender.WITH_ACTION)) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_task_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_task_item_simple, parent, false);
        }

        return new ViewHolder(view, typeRender, onChangeListener);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.taskList = taskList;
        holder.bindView(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    enum TypeRender {WITH_ACTION, WITHOUT_ACTION}


    public interface OnChangeListener {
        void onChangeStatus(Task todo);

        void onDelete(Task todo, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final TypeRender typeRender;
        private OnChangeListener onChangeListener;
        private List<Task> taskList;
        private CheckBox status;
        private ImageButton delete;

        public ViewHolder(View itemView, TypeRender typeRender, OnChangeListener onChangeListener) {
            super(itemView);

            this.typeRender = typeRender;
            this.description = (TextView) itemView.findViewById(R.id.description);

            if (typeRender.equals(TypeRender.WITH_ACTION)) {

                this.onChangeListener = onChangeListener;

                delete = (ImageButton) itemView.findViewById(R.id.delete);
                status = (CheckBox) itemView.findViewById(R.id.bit_status);

                status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Task task = taskList.get(getAdapterPosition());
                        task.setStatus(status.isChecked());
                        ViewHolder.this.onChangeListener.onChangeStatus(task);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Task task = taskList.get(getAdapterPosition());
                        task.setStatus(status.isChecked());
                        ViewHolder.this.onChangeListener.onDelete(task, getAdapterPosition());
                    }
                });
            }

        }

        public void bindView(Task task) {
            if (task.isStatus()) {
                description.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                description.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            }
            description.setText(task.getMessage());
            if (typeRender.equals(TypeRender.WITH_ACTION)) {
                status.setChecked(task.isStatus());
            }
        }
    }
}
