package tinylanglexer;
import java.util.HashMap;
import java.util.Map;
public class TransitionTable {
	protected Map<TransitionInput,State> buildTransitionTable(){
		Map<TransitionInput,State> transitionTable = new HashMap<TransitionInput,State>();
		State fromState;
		/************** transition table row 1 *******************************************/
		fromState = State.STARTING_STATE;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_4);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_11);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_4);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_5);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_14);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_16);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_16);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_14);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_15);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_16);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_17);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_1);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_10);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 1 *******************************************/
		/************** transition table row 2 *******************************************/
		fromState = State.STATE_1;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_2);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 2 *******************************************/
		/************** transition table row 3 *******************************************/
		fromState = State.STATE_2;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_3);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 3 *******************************************/
		/************** transition table row 4 *******************************************/
		fromState = State.STATE_3;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 4 *******************************************/
		/************** transition table row 5 *******************************************/
		fromState = State.STATE_4;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_4);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_4);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_4);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 5 *******************************************/
		/************** transition table row 6 *******************************************/
		fromState = State.STATE_5;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_6);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 6 *******************************************/
		/************** transition table row 7 *******************************************/
		fromState = State.STATE_6;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 7 *******************************************/
		/************** transition table row 8 *******************************************/
		fromState = State.STATE_7;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_8);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), fromState);
		/************** end transition table row 8 *******************************************/
		/************** transition table row 9 *******************************************/
		fromState = State.STATE_8;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_9);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_7);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_7);
		/************** end transition table row 9 *******************************************/
		/************** transition table row 10 *******************************************/
		fromState = State.STATE_9;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 10 *******************************************/
		/************** transition table row 11 *******************************************/
		fromState = State.STATE_10;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 11 *******************************************/
		
		/************** transition table row 12 *******************************************/
		fromState = State.STATE_11;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_11);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_12);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 12 *******************************************/
		
		/************** transition table row 13 *******************************************/
		fromState = State.STATE_12;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_13);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 13 *******************************************/
		
		/************** transition table row 14 *******************************************/
		fromState = State.STATE_13;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), fromState);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 14 *******************************************/
		/************** transition table row 15 *******************************************/
		fromState = State.STATE_14;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 15 *******************************************/
		/************** transition table row 16 *******************************************/
		fromState = State.STATE_15;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_18);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 16 *******************************************/
		/************** transition table row 17 *******************************************/
		fromState = State.STATE_16;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_19);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 17 *******************************************/
		/************** transition table row 18 *******************************************/
		fromState = State.STATE_17;
		transitionTable.put(new TransitionInput(fromState,InputCategory.LETTER), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DIGIT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.UNDERSCORE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SLASH_DIVIDE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.ASTERISK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LESS_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.GREATER_THAN), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PLUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.HYPHEN_MINUS), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EQUAL), State.STATE_19);
		transitionTable.put(new TransitionInput(fromState,InputCategory.EXCLAMATION_MARK), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.DOT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.SINGLE_QUOTE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.PUNCT), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.OTHER_PRINTABLE), State.STATE_ERROR);
		transitionTable.put(new TransitionInput(fromState,InputCategory.LINE_FEED), State.STATE_ERROR);
		/************** end transition table row 18 *******************************************/
		/************** transition table row 19 *******************************************/
		fromState = State.STATE_18;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 19 *******************************************/
		/************** transition table row 20 *******************************************/
		fromState = State.STATE_19;
		for (InputCategory input : InputCategory.values()) {
			transitionTable.put(new TransitionInput(fromState,input), State.STATE_ERROR);
		}
		/************** end transition table row 20 *******************************************/	
		return transitionTable;
	}
}