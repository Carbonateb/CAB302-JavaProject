package Shared.Schedule;

/**
 * In a repeating event, the start and end times
 */

public class RepeatingEvent extends Event {
	long rep;

	public RepeatingEvent(long inStartTime, long inEndTime, int inBillboardID, String inAuthor) {
		super(inStartTime, inEndTime, inBillboardID, inAuthor);
	}
}
