package tinylanglexer;

import java.util.Objects;

/**
 * <p>A transition <code> (FromState × Input) ↦ (ToState) </code>.</p>
 * 
 * <p>The input is given by  <code> (FromState × Input) </code>.</p>
 * 
 * @author andimon
 *
 */
public class TransitionInput {
	private int hashCode;
	private State fromState;
	private InputCategory input;
	/**
	 * Constructor for class TransitionInput
	 * @param fromState
	 * @param input
	 */
	public TransitionInput(State fromState,InputCategory input){	
		this.fromState = fromState;
		this.input = input;
		this.hashCode = Objects.hash(fromState,input);
	}
	
	public void setFromState(State fromState){
		this.fromState = fromState;
	}
	
	public void setInput(InputCategory input){
		this.input = input;
	}
	
	public State getFromState() {
		return this.fromState;
	}
	
	public InputCategory getInput() {
		return this.input;
	}
	
	// Required methods for custom key classes.
	
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TransitionInput that = (TransitionInput) o;
        return fromState == that.fromState && input == that.input;
    }
    @Override
    public int hashCode() {
        return this.hashCode;
    }


}