package com.stupidplebs.confectiondistrict.spaces

import com.stupidplebs.confectiondistrict.spaces.FinishSpace;

import spock.lang.Specification

class FinishSpaceSpec extends Specification {
	def "instance should be unequal to null"() {
		given:
		def space = new FinishSpace()
				
		and:
		def other = null
		
		expect:
		!space.equals(other)
		
	}
	
	def "instance should be equal to itself"() {
		given:
		def space = new FinishSpace()
		
		and:
		def other = space
		
		expect:
		space.equals(other)
		
	}
	
	def "instance should be unequal to a non-Player object"() {
		given:
		def space = new FinishSpace()
		
		and:
		def other = "this is not a FinishSpace object"
		
		expect:
		!space.equals(other)
		
	}
	
	def "2 instances of StartSpace should be equal and have equal hashCodes"() {
		given:
		def space = new FinishSpace()
		
		and:
		def other = new FinishSpace()
		
		expect:
		space.equals(other)
		space.hashCode() == other.hashCode()

	}
	
	def "toString should output class name"() {
		given:
		def space = new FinishSpace()
		
		expect:
		space.toString() == "FinishSpace{}"

	}
	
}
