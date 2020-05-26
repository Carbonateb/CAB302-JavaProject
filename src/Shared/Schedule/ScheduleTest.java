package Shared.Schedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    void testRun() {
    	Schedule s = new Schedule();

    	Event eventA = new Event(sec(0), sec(10), 0, "eventA");

    	s.scheduleEvent(eventA);
    }

    private long sec(long s) {
    	return System.currentTimeMillis() + (seconds * 1000);
	}
}
