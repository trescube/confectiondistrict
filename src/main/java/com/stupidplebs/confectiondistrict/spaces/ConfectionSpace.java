package com.stupidplebs.confectiondistrict.spaces;

import java.util.Objects;

import com.stupidplebs.confectiondistrict.Confection;

public class ConfectionSpace implements Space {
	private final Confection confection;
	
	public ConfectionSpace(final Confection confection) {
		if (null == confection) {
			throw new NullPointerException("confection cannot be null");
		}

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
		
		final ConfectionSpace other = (ConfectionSpace)obj;
		
		return confection.equals(other.getConfection());
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(confection);
	}
	
	@Override
	public String toString() {
		return "ConfectionSpace{confection=" + confection + "}";
	}
	
}
