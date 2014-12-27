package Misc;

import org.flixel.FlxPoint;
import org.flixel.FlxText;

/**
 * Formats the code something like text: + " " variable
 * @author Andrew Lau
 *
 */
public class VariableText extends FlxText {

	public boolean colon;
	
	public int variable = 0;
	public String variableName;
	
	public VariableText(float X, float Y, int Width) {
		super(X, Y, Width);
	}
	
	/**
	 * Use this one, embedded font is false for now
	 * @param x
	 * @param y
	 * @param variablet
	 */
	public VariableText(int x, int y, String variableName, int variable, boolean scrollable){
		super(x, y, 1000, "", false);
		
		this.variable = variable;
		this.variableName = variableName;
		setText(variableName + ": " + variable);
		
		if(!scrollable){
			scrollFactor = new FlxPoint(0, 0);
		}
		
	}
	

	/**
	 * Use this one, embedded font is false for now
	 * @param x
	 * @param y
	 * @param variablet
	 */
	public VariableText(int x, int y, String variableName, int variable, boolean scrollable, boolean colon){
		super(x, y, 1000, "", false);
		
		this.variable = variable;
		this.variableName = variableName;
		
		if(colon){
		setText(variableName + ": " + variable);
		} else {
			setText(variableName + " " + variable);
		}
		
		if(!scrollable){
			scrollFactor = new FlxPoint(0, 0);
		}
		
		this.colon = colon;
		
	}
	
	/**
	 * Returns the variable that is stored in this
	 * @return
	 */
	public int getVariable(){
		return variable;
	}
	
	/**
	 * Updates the text and the variable together
	 * @param variable
	 */
	public void setVariable(int variable){
		this.variable = variable;
		if(colon){
		setText(variableName + ": " + variable);
		} else {
			setText(variableName + " " + variable);
		}
	}


}
