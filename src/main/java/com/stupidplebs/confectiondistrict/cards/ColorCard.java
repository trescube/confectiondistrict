package com.stupidplebs.confectiondistrict.cards;

import java.util.Objects;

import com.stupidplebs.confectiondistrict.Color;

public class ColorCard implements Card {
	private final Color color;
	private final Integer numberOfMoves;
	
	public static Card getSingleMove(final Color color) {
		return new ColorCard(color, 1);
	}
	
	public static Card getDoubleMove(final Color color) {
		return new ColorCard(color, 2);
	}
	
	private ColorCard(final Color color, final Integer moveCount) {
		if (null == color) {
			throw new NullPointerException("color cannot be null");
		}
		
		this.color = color;
		this.numberOfMoves = moveCount;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Integer getNumberOfMoves() {
		return numberOfMoves;
	}

	@Override
	public boolean equals(final Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final ColorCard other = (ColorCard)obj;
		
		return color.equals(other.getColor()) &&
				numberOfMoves == other.numberOfMoves;
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(color, numberOfMoves);
	}
	
	@Override
	public String toString() {
		return "ColorCard{color=" + color + ", numberOfMoves=" + numberOfMoves + "}";
	}
	
}
