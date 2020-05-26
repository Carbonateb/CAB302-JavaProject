package Shared.Schedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    void testRun() {
    	Schedule schedule = new Schedule();

    	// Create an event that goes from 0 to 5
    	assertTrue(schedule.addEvent(new Event(0, 5, 0, 0)));

    	// Create an event from 5 to 10, this should work too
    	assertTrue(schedule.addEvent(new Event(5, 10, 0, 0)));

    	// This event (4 to 6) should NOT be valid, as it overlaps with the one created earlier (0 to 5)
		assertFalse(schedule.canFit(4, 6));


		// Removes the first event we added (the 0 to 5 one)
		assertTrue(schedule.removeEvent(0));

		// Verify it got removed by adding another event that would otherwise conflict with it if it were still there
		assertTrue(schedule.canFit(1, 4));


    }
}
