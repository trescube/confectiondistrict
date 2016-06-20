package com.stupidplebs.confectiondistrict.players

import com.stupidplebs.confectiondistrict.players.Player;

import spock.lang.Specification;

class PlayerSpec extends Specification {
	def "null name parameter should throw NullPointerException"() {
		when:
		new Player(null)
		
		then:
		NullPointerException e = thrown()
		e.message == "name cannot be null"
		
	}

	def "blank/empty name parameter should throw IllegalArgumentException"() {
		when:
		new Player(name)
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "name cannot be blank"

		where:
		name << ["", " \t "]
		
	}
	
	def "getName should return value supplied but trimmed"() {
		given:
		def player = new Player(" \t Player Name \t ")
		
		expect:
		player.name == "Player Name"

	}
	
	def "instance should be unequal to null"() {
		given:
		def player = new Player("Player Name")
		
		and:
		def other = null
		
		expect:
		!player.equals(other)
		
	}
	
	def "instance should be equal to itself"() {
		given:
		def player = new Player("Player Name")
		
		and:
		def other = player
		
		expect:
		player.equals(other)
		
	}
	
	def "instance should be unequal to a non-Player object"() {
		given:
		def player = new Player("Player Name")
		
		and:
		def other = "this is not a Player object"
		
		expect:
		!player.equals(other)
		
	}
	
	def "instances differing on name should be unequal and have unequal hashCodes"() {
		given:
		def player = new Player("Player Name")
		
		and:
		def other = new Player("Different Player Name")
		
		expect:
		!player.equals(other)
		player.hashCode() != other.hashCode()

	}
	
	def "instances with equal names should be equal and have equal hashCodes"() {
		given:
		def player = new Player("Player Name")
		
		and:
		def other = new Player("Player Name")
		
		expect:
		player.equals(other)
		player.hashCode() == other.hashCode()

	}
	
	def "toString should output name"() {
		given:
		def player = new Player("Player Name")
		
		expect:
		player.toString() == "Player{name=Player Name}"

	}
	
}
