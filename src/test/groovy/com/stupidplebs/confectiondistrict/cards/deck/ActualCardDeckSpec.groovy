package com.stupidplebs.confectiondistrict.cards.deck

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Confection
import com.stupidplebs.confectiondistrict.Color
import com.stupidplebs.confectiondistrict.cards.ConfectionCard
import com.stupidplebs.confectiondistrict.cards.ColorCard

class ActualCardDeckSpec extends Specification {
	def "null color parameter to singleMoveCard should throw NullPointerException"() {
		when:
		new ActualCardDeck.Builder().singleMoveCard(null, 3)
		
		then:
		NullPointerException e = thrown()
		e.message == "color cannot be null"
		
	}
	
	def "null quantity parameter to singleMoveCard should throw NullPointerException"() {
		when:
		new ActualCardDeck.Builder().singleMoveCard(Color.YELLOW, null)
		
		then:
		NullPointerException e = thrown()
		e.message == "quantity cannot be null"

	}
	
	def "null color parameter to doubleMoveCard should throw NullPointerException"() {
		when:
		new ActualCardDeck.Builder().doubleMoveCard(null, 3)
		
		then:
		NullPointerException e = thrown()
		e.message == "color cannot be null"

	}
	
	def "null quantity parameter to doubleMoveCard should throw NullPointerException"() {
		when:
		new ActualCardDeck.Builder().doubleMoveCard(Color.YELLOW, null)
		
		then:
		NullPointerException e = thrown()
		e.message == "quantity cannot be null"

	}
	
	def "null confection parameter to confectionCard should throw NullPointerException"() {
		when:
		new ActualCardDeck.Builder().confectionCard(null)

		then:
		NullPointerException e = thrown()
		e.message == "confection cannot be null"

	}
	
	def "shuffleDeck should return deck with all cards present"() {
		given:
		def deck = new ActualCardDeck.Builder().
				confectionCard(Confection.BLINTZ).
				confectionCard(Confection.BUBBLE_GUM).
				singleMoveCard(Color.BLUE, 3).
				singleMoveCard(Color.RED, 4).
				doubleMoveCard(Color.YELLOW, 2).
				doubleMoveCard(Color.PURPLE, 5).
				build()

		def expectedCardHistogram = [:]
		expectedCardHistogram[new ConfectionCard(Confection.BLINTZ)] = 1
		expectedCardHistogram[new ConfectionCard(Confection.BUBBLE_GUM)] = 1
		expectedCardHistogram[ColorCard.getSingleMove(Color.BLUE)] = 3
		expectedCardHistogram[ColorCard.getSingleMove(Color.RED)] = 4
		expectedCardHistogram[ColorCard.getDoubleMove(Color.YELLOW)] = 2
		expectedCardHistogram[ColorCard.getDoubleMove(Color.PURPLE)] = 5
		
		when:
		def shuffledDeck = deck.shuffle()
		
		def cardHistogram = [:]
		while (!shuffledDeck.empty) {
			def card = shuffledDeck.pop()
			if (!cardHistogram[card]) {
				cardHistogram[card] = 0
			}
			cardHistogram[card]++
		}
		
		then:
		cardHistogram == expectedCardHistogram
		
	}
	
}
