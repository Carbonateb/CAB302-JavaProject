package Shared.Schedule;

// import org.junit.jupiter.api.Test;

import Shared.Permissions.Perm;
import Shared.Permissions.Permissions;

import java.util.Timer;
import java.util.TimerTask;

// import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

	/** Launches a console that debugs Schedule */
	public static void main(String args[]) {
		// Create the source permissions thing, populate it
		Permissions originalP = new Permissions();
		originalP.addPermission(Perm.CREATE_BILLBOARDS);
		originalP.addPermission(Perm.EDIT_USERS);
		System.out.println(originalP.toString());

		// Create a new permissions, using the constructor that takes in an int
		Permissions newP = new Permissions(originalP.toint());
		System.out.println(newP.toString());


		if (true) {return;}

		final Schedule s = new Schedule();
		s.scheduleEvent(new RepeatingEvent(sec(3), sec(6), 0, "Repeating event", 5 * 1000));
		s.scheduleEvent(new Event(sec(1), sec(5), 0, "My Event 1"));


		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			/** Contact the server for info here */
			@Override
			public void run() {
				System.out.println("\n\n\n\nSCHEDULE INFO:");

				System.out.println("The selected event:");
				System.out.printf("\t%s\n", s.getEvent().author);

				System.out.println("Upcoming Events:");
				for (Event e : s.upcomingEvents) {
					System.out.printf("\t%s\n", e.author);
				}

				System.out.println("Active Events:");
				for (Event e : s.activeEvents) {
					System.out.printf("\t%s\n", e.author);
				}

			}

		}, 0, 1000);

	}


    // @Test
    // void testRun() {
    // 	Schedule s = new Schedule();

    // 	// Create some events that start immediately
    // 	Event eventA = new Event(sec(-1), sec(10), 0, "eventA");
	// 	Event eventB = new Event(sec(-1), sec(10), 0, "eventB");

	// 	// This event starts in 100 seconds, so it shouldn't be made active
	// 	Event eventC = new Event(sec(100), sec(1000), 0, "eventC");

	// 	// This event started and ended a few seconds ago. It should be made active then removed immediately
	// 	Event oldEvent = new Event(sec(-4), sec(-1), 0, "oldEvent");


	// 	// Test that an event can be added, active immediately
    // 	s.scheduleEvent(eventA);
    // 	assertEquals("eventA", s.getEvent().author);

    // 	// Test that adding another event will replace the old one on the fly
	// 	s.scheduleEvent(eventB);
	// 	assertEquals("eventB", s.getEvent().author);

	// 	// Test that adding an event scheduled for later does NOT activate it now
	// 	s.scheduleEvent(eventC);
	// 	assertNotEquals("eventC", s.getEvent().author);

	// 	// Test that adding an old event doesn't break things
	// 	s.scheduleEvent(oldEvent);
	// 	assertNotEquals("oldEvent", s.getEvent().author);
    // }

    private static long sec(long s) {
    	return System.currentTimeMillis() + (s * 1000);
	}
}
