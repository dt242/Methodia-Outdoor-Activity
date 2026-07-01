package com.academy.outdooractivity.calendar;

import com.academy.outdooractivity.model.ActivityResult;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleCalendarService implements CalendarService {

    private final Calendar googleCalendar;
    private final CalendarEventMapper mapper;

    public GoogleCalendarService(Calendar googleCalendar, CalendarEventMapper mapper) {
        this.googleCalendar = googleCalendar;
        this.mapper = mapper;
    }

    @Override
    public void createEvents(List<ActivityResult> results) {
        if (results.isEmpty()) {
            return;
        }
        List<Event> events = mapper.mapToEvents(results);

        for (Event event : events) {
            try {
                googleCalendar.events().insert("primary", event).execute();
                System.out.println("Event created!");
            } catch (Exception e) {
                System.err.println("Error creating event: " + e.getMessage());
            }
        }
    }
}