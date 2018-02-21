package com.dreamsofpines.mcunost.data.storage.pattern;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThePupsick on 05.02.2018.
 */

public class EventManager {

    public interface EventListener{
        void update(String type);
    }

    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operation){
        for(String str: operation){
            this.listeners.put(str,new ArrayList<EventListener>());
        }
    }

    public void subscribe(String eventType, EventListener listener){
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void notify(String eventType) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType);
        }
    }


}
