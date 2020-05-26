package Shared.Schedule;

/**
 * The Schedule manages a collection of Events. You can add billboards to the Schedule by adding Events.
 * Complex behaviour like looping events is also supported here.
 *
 * @author Lucas Maldonado n10534342
 */
public class Schedule {
	

	// Constructor
	public Schedule() {
	}


	private long getTime() {
		return System.currentTimeMillis();
	}

}
