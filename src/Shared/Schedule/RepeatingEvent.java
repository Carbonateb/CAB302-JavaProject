package Shared.Schedule;

/**
 * Repeating event adds a loop interval and some helper methods
 *
 * @author Lucas Maldonado - n10534342
 */

public class RepeatingEvent extends Event {
	long loopInterval;

	/***
	 * for repeating an Event
	 * @param inStartTime long unix time of the start time
	 * @param inEndTime long unix time of end time
	 * @param inBillboardName string, billboard name
	 * @param inAuthor string, author
	 * @param inloopInterval long, contains the loop interval
	 */
	public RepeatingEvent(long inStartTime, long inEndTime, String inBillboardName, String inAuthor, long inloopInterval) {
		super(inStartTime, inEndTime, inBillboardName, inAuthor);
		loopInterval = inloopInterval;
	}


	/***
	 * Constructor that calculates when this event should display based off the past one.
	 * @param oldRE RepeatingEvent
	 */
	public RepeatingEvent(RepeatingEvent oldRE) {
		super(
			oldRE.startTime + oldRE.loopInterval,
			oldRE.endTime + oldRE.loopInterval,
			oldRE.billboardName,
			oldRE.author);
		loopInterval = oldRE.loopInterval;
		//System.out.printf("Remade RE: Old end: %d, new end is %d. Difference of %d", oldRE.endTime, endTime, endTime - oldRE.endTime);
	}
}
