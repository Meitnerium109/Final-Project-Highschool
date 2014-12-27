package Misc;

public class RandomNumber {

	/**
	 * Generates a randonNumber between the low range and the high range
	 * inclusive and returns it
	 * 
	 * @param lowRange
	 * @param highRange
	 */
	public static int generateRandomNumber(int lowRange, int highRange) {
		int randomNumber = (int) (Math.random() * (highRange - lowRange + 1) + lowRange);
		return randomNumber;

	}
}
