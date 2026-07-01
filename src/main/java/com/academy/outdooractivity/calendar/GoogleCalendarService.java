package com.academy.outdooractivity.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleCalendarService implements CalendarService {

    private final Calendar googleCalendar;

    public GoogleCalendarService(Calendar googleCalendar) {
        this.googleCalendar = googleCalendar;
    }

    @Override
    public void createEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

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