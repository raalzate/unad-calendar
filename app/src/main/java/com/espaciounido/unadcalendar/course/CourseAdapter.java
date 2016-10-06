package com.espaciounido.unadcalendar.course;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.course.domain.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 5/09/16.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ItemViewHolder> {

    private List<Course> courses;
    private OnItemClickListener listener;

    public CourseAdapter(List<Course> courses, OnItemClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_row, viewGroup, false);
        return new ItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(courses.get(position));
    }

    public void setFilter(List<Course> courses) {
        this.courses = new ArrayList<>();
        this.courses.addAll(courses);
        notifyDataSetChanged();
    }

    public Course getItem(int position) {
        return courses.get(position);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView code;
        private OnItemClickListener listener;

        public ItemViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            code = (TextView) itemView.findViewById(R.id.code);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void bind(Course course) {
            name.setText(course.name);
            code.setText(String.valueOf(course.code));
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }

    }
}
