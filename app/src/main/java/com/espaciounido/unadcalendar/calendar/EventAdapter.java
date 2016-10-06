package com.espaciounido.unadcalendar.calendar;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.domain.ItemEvent;
import com.espaciounido.unadcalendar.calendar.domain.Task;
import com.espaciounido.unadcalendar.calendar.domain.usecase.GetTaskUseCase;
import com.espaciounido.unadcalendar.data.repo.todo.TodoRepo;
import com.espaciounido.unadcalendar.utils.DateUtils;
import com.espaciounido.unadcalendar.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnClickEventListener eventListener;
    private final GetTaskUseCase getTaskUseCase;

    private List<ItemEvent> mValues;


    public EventAdapter(List<ItemEvent> items, OnClickEventListener eventListener) {
        mValues = items;
        this.eventListener = eventListener;
        this.getTaskUseCase = new GetTaskUseCase(new TodoRepo());

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cal_item, parent, false);
        return new ItemEventViewHolder(view, eventListener);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ItemEventViewHolder viewHolder = (ItemEventViewHolder) holder;
        viewHolder.mItem = mValues.get(position);

        String date = String.format("%s / %s",
                Utils.getDataByFormat(viewHolder.mItem.start, "EEE d MMM"),
                Utils.getDataByFormat(viewHolder.mItem.end, "EEE d MMM"));

        viewHolder.date.setText(date.toUpperCase());
        viewHolder.title.setText(viewHolder.mItem.title);
        viewHolder.detail.setText(viewHolder.mItem.detail);

        int dayRest = DateUtils
                .restDay(java.util.Calendar.getInstance().getTime(), viewHolder.mItem.end);

        viewHolder.mView.setBackgroundColor(Utils.defineColorBayDay(dayRest));

        UseCaseHandler.getInstance().execute(getTaskUseCase,
                new GetTaskUseCase.Request(viewHolder.mItem.id),
                new UseCase.UseCaseCallback<GetTaskUseCase.Response>() {
            @Override
            public void onSuccess(GetTaskUseCase.Response response) {
                viewHolder.todoList.setAdapter(new TaskAdapter(response.getData()));
            }

            @Override
            public void onError() {

            }
        });

    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnClickEventListener {
        void onClickEvent(ItemEvent item);
    }

    public class ItemEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView title;
        public final TextView detail;
        public final RecyclerView todoList;
        public final TextView date;
        private final OnClickEventListener onClickEventListener;

        public ItemEvent mItem;

        public ItemEventViewHolder(View view, OnClickEventListener onListener) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            detail = (TextView) view.findViewById(R.id.detail);
            todoList = (RecyclerView) view.findViewById(R.id.todo_list);
            date = (TextView) view.findViewById(R.id.date);

            todoList.setHasFixedSize(true);
            todoList.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            onClickEventListener = onListener;
            mView.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
            onClickEventListener.onClickEvent(mItem);
        }
    }
}
