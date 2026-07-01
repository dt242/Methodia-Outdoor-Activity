package com.academy.outdooractivity.calendar;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.TimeInterval;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarEventMapper {

    public List<Event> mapToEvents(List<ActivityResult> results) {
        List<Event> events = new ArrayList<>();

        for (ActivityResult result : results) {
            for (DayResult day : result.dayResults()) {
                for (TimeInterval interval : day.intervals()) {
                    Event event = new Event()
                            .setSummary(result.sportName() + " - Perfect conditions!")
                            .setDescription("It's time for " + result.sportName() + "!");
                    ZonedDateTime startZdt = day.date().atTime(interval.startHour(), 0).atZone(ZoneId.systemDefault());
                    ZonedDateTime endZdt = day.date().atTime(interval.endHour(), 0).atZone(ZoneId.systemDefault());

                    EventDateTime start = new EventDateTime()
                            .setDateTime(new DateTime(startZdt.toInstant().toEpochMilli()))
                            .setTimeZone(ZoneId.systemDefault().getId());
                    event.setStart(start);

                    EventDateTime end = new EventDateTime()
                            .setDateTime(new DateTime(endZdt.toInstant().toEpochMilli()))
                            .setTimeZone(ZoneId.systemDefault().getId());
                    event.setEnd(end);
                    events.add(event);
                }
            }
        }

        return events;
    }
}