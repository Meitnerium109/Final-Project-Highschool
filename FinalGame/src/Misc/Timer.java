package Misc;

/**
 * Times a bunch of stuff
 * @author Andrew Lau
 *
 */
public class Timer {
	
	public static final int FRAMES_TO_SECONDS = 60;
	public int currentFrame = 0;
	public int totalFrames;
	
	public boolean started = false;
	public boolean finished = true;
	public boolean lastFrame = false;
	
	public Timer(int numFrames){
		totalFrames = numFrames;
	}
	
	public void update(){
		if(started && currentFrame < totalFrames){
			if(currentFrame == totalFrames - 1){
				lastFrame = true;
			}
			currentFrame++;
		} else {
			finished = true;
			started = false;
			lastFrame = false;
		}
	}
	
	/**
	 * Restarts the timer. The timer instantly goes again
	 */
	public void restart(){
		currentFrame = 0;
		finished = false;
		started = true;
	}
	
	/**
	 * Resets the timer but does not start it
	 */
	public void reset(){
		currentFrame = 0;
		finished = false;
		started = false;
	}
	
	/**
	 * Starts the timer if everything was already reset
	 */
	public void start(){
		started = true;
	}
	
	/**
	 * Sets the total number of frames
	 * @param numFrames
	 */
	public void setTotalFrames(int numFrames){
		totalFrames = numFrames;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public boolean isLastFrame(){
		return lastFrame;
	}
	
	public boolean isFrameNumber(int frameNumber){
		return currentFrame == frameNumber;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	/*public boolean isLastPercentage(int percent){
		
    }*/
}
