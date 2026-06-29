package com.academy.outdooractivity.ui;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.TimeInterval;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsolePrinter {

    public void print(List<ActivityResult> results) {
        for (ActivityResult result : results) {
            System.out.printf("%n=== %s ===%n", result.sportName().toUpperCase());
            if (!result.hasIntervals()) {
                System.out.println("No suitable intervals found.");
                continue;
            }

            for (DayResult dayResult : result.dayResults()) {
                System.out.printf("%nDate: %s", dayResult.date());
                if (dayResult.preferredWeekend()) {
                    System.out.print(" (preferred weekend)");
                }
                System.out.println("\nIntervals:");

                for (TimeInterval interval : dayResult.intervals()) {
                    System.out.println("  - " + interval);
                }
            }
        }
    }
}