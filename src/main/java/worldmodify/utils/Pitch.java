package worldmodify.utils;

public enum Pitch {
	
	UP, UP_DIAGONAL, STRAIGHT, DOWN_DIAGONAL, DOWN;
	
	public static Pitch pitchToPitch(float pitch){
		if(pitch == 90) return DOWN;
		if(pitch == -90) return UP;
		if(pitch > -90 && pitch <= -30) return UP_DIAGONAL;
		if(pitch >= 30 && pitch < 90) return DOWN_DIAGONAL;
		return STRAIGHT;
	}
	
}
