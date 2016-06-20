package com.stupidplebs.confectiondistrict.cards;

import java.util.Objects;

import com.stupidplebs.confectiondistrict.Confection;

public class ConfectionCard implements Card {
	private final Confection confection;
	
	public ConfectionCard(final Confection confection) {
		this.confection = confection;
	}
	
	public Confection getConfection() {
		return confection;
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
		
		final ConfectionCard other = (ConfectionCard)obj;
		
		return confection.equals(other.getConfection());
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(confection);
	}
	
	@Override
	public String toString() {
		return "ConfectionCard{confection=" + confection + "}";
	}
	
}
