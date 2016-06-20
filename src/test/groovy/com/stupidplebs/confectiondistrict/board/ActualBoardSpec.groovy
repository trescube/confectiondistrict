package com.stupidplebs.confectiondistrict.board

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Confection
import com.stupidplebs.confectiondistrict.Color
import com.stupidplebs.confectiondistrict.cards.ConfectionCard
import com.stupidplebs.confectiondistrict.cards.Card
import com.stupidplebs.confectiondistrict.cards.ColorCard
import com.stupidplebs.confectiondistrict.spaces.ConfectionSpace
import com.stupidplebs.confectiondistrict.spaces.ColorSpace
import com.stupidplebs.confectiondistrict.spaces.FinishSpace
import com.stupidplebs.confectiondistrict.spaces.StartSpace

class ActualBoardSpec extends Specification {
	def "getAllSpaces should return all spaces added"() {
		given:
		def board = new ActualBoard.Builder().
			sticky(Color.RED).
			trailhead(Color.GREEN, 2).
			confection(Confection.BLINTZ).
			color(Color.YELLOW).
			build()
			
		expect:
		board.allSpaces == [
			new StartSpace(),
			new ColorSpace(Color.RED, true),
			new ColorSpace(Color.GREEN, 2),
			new ConfectionSpace(Confection.BLINTZ),
			new ColorSpace(Color.YELLOW),
			new FinishSpace()
		]
		
		
	}
	
	def "null currentSpace parameter should throw NullPointerException"() {
		given:
		def board = new ActualBoard.Builder().build()
		
		when:
		board.takeTurn(null, Mock(Card))
		
		then:
		NullPointerException e = thrown()
		e.message == "currentSpace cannot be null"
		
	}
	
	def "null card parameter should throw NullPointerException"() {
		given:
		def board = new ActualBoard.Builder().build()
		
		when:
		board.takeTurn(3, null)
		
		then:
		NullPointerException e = thrown()
		e.message == "card cannot be null"
		
	}
	
	def "currentSpace below valid range should throw IllegalArgumentException"() {
		given:
		def board = new ActualBoard.Builder().build()
		
		when:
		board.takeTurn(-1, Mock(Card))
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "currentSpace outside of range"
		
	}
	
	def "currentSpace beyond valid range should throw IllegalArgumentException"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()
		
		when:
		board.takeTurn(board.allSpaces.size()+1, Mock(Card))
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "currentSpace outside of range"
		
	}
	
	def "playing an unsupported Confection card should throw IllegalArgumentException"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()
		
		when:
		board.takeTurn(0, new ConfectionCard(Confection.BLINTZ))
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "BLINTZ space not found on board"

	}
	
	def "playing from finish space should return finish space regardless of card content"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				confection(Confection.BLINTZ).
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(4, new ConfectionCard(Confection.BLINTZ))
		
		then:
		newSpace == 4

	}
	
	def "playing supported Confection card should return that space"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				confection(Confection.BLINTZ).
				color(Color.RED).
				confection(Confection.BUBBLE_GUM).
				color(Color.YELLOW).
				build()
				
		when:
		def newSpace = board.takeTurn(0, new ConfectionCard(Confection.BUBBLE_GUM))
		
		then:
		newSpace == 4
		
	}
	
	def "playing Color card while on sticky space should not move if Color doesn't match"() {
		given:
		def board = new ActualBoard.Builder().
				sticky(Color.BLUE).
				color(Color.RED).
				color(Color.BLUE).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.GREEN))
		
		then:
		newSpace == 1

	}
	
	def "playing Color card while on sticky space should move if Color matches"() {
		given:
		def board = new ActualBoard.Builder().
				sticky(Color.BLUE).
				color(Color.RED).
				color(Color.BLUE).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.BLUE))
		
		then:
		newSpace == 3

	}
	
	def "playing color card from trailhead that matches color of terminus should stop at terminus"() {
		given:
		def board = new ActualBoard.Builder().
				trailhead(Color.BLUE, 3).
				color(Color.RED).
				color(Color.BLUE).
				color(Color.YELLOW). // terminus space
				color(Color.BLUE).
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.BLUE))
		
		then:
		newSpace == 4

	}
	
	def "playing color card from trailhead that doesn't match color of terminus should advance to color"() {
		given:
		def board = new ActualBoard.Builder().
				trailhead(Color.BLUE, 4).
				color(Color.RED).
				color(Color.BLUE).
				color(Color.YELLOW).
				color(Color.BLUE). // terminus space
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.BLUE))
		
		then:
		newSpace == 5

	}
	
	def "playing 2-move card should advance 2 spaces that match on color"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				color(Color.BLUE).
				color(Color.YELLOW).
				color(Color.BLUE).
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getDoubleMove(Color.BLUE))
		
		then:
		newSpace == 5

	}
	
	def "playing 2-move card from trailhead that matches color of terminus should include terminus as 1 move"() {
		given:
		def board = new ActualBoard.Builder().
				trailhead(Color.BLUE, 2).
				color(Color.RED).
				color(Color.BLUE). // terminus
				color(Color.YELLOW).
				color(Color.BLUE).
				color(Color.RED).
				color(Color.BLUE).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getDoubleMove(Color.BLUE))
		
		then:
		newSpace == 5

	}
	
	def "trail should not be taken if it's not the starting space"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				trailhead(Color.YELLOW, 2).
				color(Color.BLUE).
				color(Color.RED).
				color(Color.BLUE).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.BLUE))
		
		then:
		newSpace == 4

	}
	
	def "card played that advances beyond last official space should return finish space"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.YELLOW))
		
		then:
		newSpace == 3
		board.isFinished(newSpace)

	}
	
	def "card played that lands on last official space should return last official space"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()
				
		when:
		def newSpace = board.takeTurn(1, ColorCard.getSingleMove(Color.RED))
		
		then:
		newSpace == 2
		board.isFinished(newSpace)
		
	}

	def "isFinished should return false if current space is not last official space or finish space"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()

		expect:
		!board.isFinished(1)

	}
	
	def "isFinished should return true if currentSpace is finish space or last official space "() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()

		expect:
		!board.isFinished(0)
		!board.isFinished(1)
		board.isFinished(2)
		board.isFinished(3)
		
	}
	
	def "null space paramter to isFinished should throw NullPointerException"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()

		when:
		board.isFinished(null)

		then:
		NullPointerException e = thrown()
		e.message == "currentSpace cannot be null"

	}
	
	def "isFinished should throw IllegalArgumentException if currentSpace is below range"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()

		when:
		board.isFinished(-1)

		then:
		IllegalArgumentException e = thrown()
		e.message == "currentSpace outside of range"
		
	}
	
	def "isFinished should throw IllegalArgumentException if currentSpace is beyond range"() {
		given:
		def board = new ActualBoard.Builder().
				color(Color.BLUE).
				color(Color.RED).
				build()

		when:
		board.isFinished(board.allSpaces.size()+1)

		then:
		IllegalArgumentException e = thrown()
		e.message == "currentSpace outside of range"

	}
	
	// need tests to validate entirety of board, ie - makes sure trailheads don't terminate outside of valid space range
	
}
