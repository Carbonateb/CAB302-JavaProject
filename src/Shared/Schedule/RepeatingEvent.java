package Shared.Schedule;

/**
 * In a repeating event, the start and end times indicate the
 */

public class RepeatingEvent extends Event {
	long loopInterval;

	public RepeatingEvent(long inStartTime, long inEndTime, int inBillboardID, String inAuthor, long inloopInterval) {
		super(inStartTime, inEndTime, inBillboardID, inAuthor);
		loopInterval = inloopInterval;
	}

	/**
	 * Constructor that calculates when this event should display based off the past one.
	 */
	public RepeatingEvent(RepeatingEvent oldRE) {
		super(
			oldRE.startTime + oldRE.loopInterval,
			oldRE.endTime + oldRE.loopInterval,
			oldRE.billboardID,
			oldRE.author);
		loopInterval = oldRE.loopInterval;
		//System.out.printf("Remade RE: Old end: %d, new end is %d. Difference of %d", oldRE.endTime, endTime, endTime - oldRE.endTime);
	}
}
