package com.espaciounido.unadcalendar.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.calendar.EventActivity;
import com.espaciounido.unadcalendar.dashboard.domain.model.ItemEvent;
import com.espaciounido.unadcalendar.dashboard.events.OnListItemsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class ListEventFragment extends Fragment {


    private RecyclerView recyclerView;
    private EventAdapter.OnClickEventListener listener;
    private List<ItemEvent> itemEvent;

    public ListEventFragment() {
        itemEvent = new ArrayList<>();
        EventBus.getDefault().register(this);
    }

    public static ListEventFragment newInstance() {
        return new ListEventFragment();
    }

    public void setListener(EventAdapter.OnClickEventListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new EventAdapter(itemEvent, new EventAdapter.OnClickEventListener() {
            @Override
            public void onClickEvent(ItemEvent item) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                intent.putExtra("id", item.id);
                getActivity().startActivity(intent);
            }
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadEventsItemList(OnListItemsEvent event) {
        itemEvent = event.itemEvent;
        if (recyclerView != null) {
            ((EventAdapter) recyclerView.getAdapter()).addAll(itemEvent);
        }

    }
}
