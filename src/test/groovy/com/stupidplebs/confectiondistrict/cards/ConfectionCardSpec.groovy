package com.stupidplebs.confectiondistrict.cards

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Confection

class ConfectionCardSpec extends Specification {
	def "getConfection should return the value passed to constructor"() {
		given:
		def confection = new ConfectionCard(Confection.BLINTZ)
		
		expect:
		confection.confection == Confection.BLINTZ
		
	}
	
	def "instance should be unequal to null"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
				
		and:
		def other = null
		
		expect:
		!card.equals(other)
		
	}
	
	def "instance should be equal to itself"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
		
		and:
		def other = card
		
		expect:
		card.equals(other)
		
	}
	
	def "instance should be unequal to a non-Player object"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
		
		and:
		def other = "this is not a ConfectionCard object"
		
		expect:
		!card.equals(other)
		
	}
	
	def "instances differing on confection should be unequal and have unequal hashCodes"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
		
		and:
		def other = new ConfectionCard(Confection.LICORICE)
		
		expect:
		!card.equals(other)
		card.hashCode() != other.hashCode()

	}
	
	def "instances with equal confection should be equal and have equal hashCodes"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
		
		and:
		def other = new ConfectionCard(Confection.BLINTZ)
		
		expect:
		card.equals(other)
		card.hashCode() == other.hashCode()

	}
	
	def "toString should output color and numberOfMoves"() {
		given:
		def card = new ConfectionCard(Confection.BLINTZ)
		
		expect:
		card.toString() == "ConfectionCard{confection=BLINTZ}"

	}
	
}
