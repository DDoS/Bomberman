package ecse321.fall2014.group3.bomberman.input;

import java.awt.event.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyboardState{
    private long pressTime;
    private boolean isPressed;
    private Key key;
    
    public KeyboardState(Key inputKey, boolean pressState, long timePressed){
        this.key = inputKey;
        this.isPressed = pressState;
        this.pressTime = timePressed;
    }
   
    public long getPressTime(){
        return pressTime;
    }
   
    public Key getKeyPressed(){
        return key;
    }
    
    public boolean getPressState(){
        return isPressed;
    }
    
    public void reset(){
        pressTime = 0;
        isPressed = false;
    }    
}
	 



