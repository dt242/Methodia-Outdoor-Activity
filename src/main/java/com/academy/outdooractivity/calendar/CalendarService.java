package com.academy.outdooractivity.calendar;

import com.google.api.services.calendar.model.Event;
import java.util.List;

public interface CalendarService {
    void createEvents(List<Event> events);
}