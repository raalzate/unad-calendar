package com.espaciounido.unadcalendar.calendar;

/**
 * Created by MyMac on 17/09/16.
 */

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
import android.widget.Toast;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.domain.ItemEvent;
import com.espaciounido.unadcalendar.calendar.domain.usecase.GetEventsByActUseCase;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventLocalRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventFragment extends Fragment implements EventAdapter.OnClickEventListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CODE_CAL = "code_cal";

    private RecyclerView recyclerView;
    private List<ItemEvent> itemEvent;

    public EventFragment() {
        itemEvent = new ArrayList<>();
    }

    public static EventFragment newInstance(int position, String code) {
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        bundle.putString(ARG_CODE_CAL, code);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_item_list, container, false);
    }

    private void loadData() {
        int section = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (section) {
            case 1:
                loadEventsByStartAndEnd(new Date(), null);
                break;
            case 2:
                loadEventsByStartAndEnd(new Date(), new Date());
                break;
            case 3:
                loadEventsByStartAndEnd(null, new Date());
                break;
        }
    }

    private void loadEventsByStartAndEnd(Date start, Date end) {
        String code = getArguments().getString(ARG_CODE_CAL);
        UseCaseHandler.getInstance().execute(new GetEventsByActUseCase(new GCEventLocalRepo()),
                new GetEventsByActUseCase.Request(start, end, code),
                new UseCase.UseCaseCallback<GetEventsByActUseCase.Response>() {
                    @Override
                    public void onSuccess(GetEventsByActUseCase.Response response) {
                        itemEvent = response.list;
                        recyclerView.setAdapter(new EventAdapter(itemEvent, EventFragment.this));
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }


    @Override
    public void onClickEvent(ItemEvent item) {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra("id", item.id);
        getActivity().startActivity(intent);
    }
}
