package com.academy.outdooractivity.calendar;

import com.academy.outdooractivity.model.ActivityResult;
import java.util.List;

public interface CalendarService {
    void createEvents(List<ActivityResult> results);
}