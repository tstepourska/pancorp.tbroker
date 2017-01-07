package com.pancorp.tbroker.model;

public enum CandlePattern {
	DOJI,				//no body (or within few pennies, either direction), short or avg shadows
	TOMBSTONE_DOJI,		//foji, long top shadow, no bottom
	DRAGONFLY_DOJI,		//foji, long lower shadow, no top
	LONG_LEGGED_DOJI,	//doji, long both shadows
	BULLISH_MARABOZU,	//white, no shadows, long body
	BEARISH_MARABOZU,	//black, no shadows, long bodu
	SPINNING_TOP,	//short body, short or avg shadows, or no shadow(s), can be black or white
	HIGH_WAVE,		//has both shadows 2-3 times longer than spinning top, can be black or white
	LONG_LOWER_SHADOW,	//black, avg body, the longer the shadow, the more bullish the candle
	LONG_UPPER_SHADOW,	//white,avg body, the longer the shadow, the more bearish the candle
	
	//complex: require checking previous values
	HANGMAN,
	HAMMER,
	TWEEZER_BOTTOM,
	TWEEZER_TOP,
	INVERTED_HAMMER,
	SHOOTING_STAR,
	BULLISH_PIERCING,
	BULLISH_ENGULFING,
	BULLISH_COUNTER_ATTACK,
	BULLISH_HARAMI,
	BULLISH_REST_AFTER_BATTLE,
	DARK_CLOUD_COVER,
	BEARISH_ENGULFING,
	BEARISH_COUNTER_ATTACK,
	BEARISH_HARAMI,
	BEARISH_REST_AFTER_BATTLE,
	ONE_BLACK_CROW,
	ONE_WHITE_SOLDIER,
	EVENING_DOJI_STAR,
	EVENING_STAR_REVERSAL,
	RISING_THREE,
	MORNING_STAR_REVERSAL,
	MORNING_DOJI_STAR,
	FALLING_THREE,
	;
	
	public static String getValue(CandlePattern p){
		switch(p){
		case DOJI:
			return "doji";
		case TOMBSTONE_DOJI:
			return "tombstone_doji";
		case DRAGONFLY_DOJI:
			return "dragonfly_doji";
		case LONG_LEGGED_DOJI:
			return "long_legged_doji";
		case BULLISH_MARABOZU:
			return "bullish_marabozu";
		case BEARISH_MARABOZU:
			return "bearish_marabozu";
		case SPINNING_TOP:
			return "spinning_top";
		case HIGH_WAVE:
			return "high_wave";
		case LONG_LOWER_SHADOW:
			return "long_lower_shadow";
		case LONG_UPPER_SHADOW:
			return "long_upper_shadow";
			default:
				return null;
		}
	}
}
