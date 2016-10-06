package com.espaciounido.unadcalendar.dashboard.events;

import com.espaciounido.unadcalendar.dashboard.domain.model.ItemEvent;

import java.util.List;

/**
 * Created by MyMac on 4/09/16.
 */
public class OnListItemsEvent {
    public final List<ItemEvent> itemEvent;

    public OnListItemsEvent(List<ItemEvent> itemEvent) {
        this.itemEvent = itemEvent;
    }
}
