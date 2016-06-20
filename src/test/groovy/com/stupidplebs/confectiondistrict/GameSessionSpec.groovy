package com.stupidplebs.confectiondistrict

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.board.Board
import com.stupidplebs.confectiondistrict.cards.deck.CardDeck
import com.stupidplebs.confectiondistrict.players.Player

class GameSessionSpec extends Specification {
    def "getBoard and getCardDeck should return values passed to constructor"() {
        given:
        def board = Mock(Board)
        def cardDeck = Mock(CardDeck)

        and:
        def gameSession = new GameSession(board, cardDeck, [])

        expect:
        gameSession.board == board
        gameSession.cardDeck == cardDeck

    }

    def "getPlayers should return an unmodifiable list"() {
        given:
        def board = Mock(Board)
        def cardDeck = Mock(CardDeck)

        and:
        def gameSession = new GameSession(board, cardDeck, [])

        when:
        gameSession.players.clear()

        then:
        thrown(UnsupportedOperationException)

    }

    def "getNumberOfGamesPlayed should return the number of games registered"() {
        given:
        def board = Mock(Board)
        def cardDeck = Mock(CardDeck)

        and:
        def player1 = new Player("player 1")
        def player2 = new Player("player 2")

        and:
        def gameSession = new GameSession(board, cardDeck, [player1, player2])

        and:
        def gameResult1 = [:]
        gameResult1[player1] = []
        gameResult1[player2] = []

        def gameResult2 = [:]
        gameResult2[player1] = []
        gameResult2[player2] = []

        when:
        gameSession.registerGame(gameResult1, 10)
        gameSession.registerGame(gameResult2, 20)

        then:
        gameSession.numberOfGamesPlayed == 2

    }

    def "getAverageGameDuration should return the total duration / number of games played"() {
        given:
        def board = Mock(Board)
        def cardDeck = Mock(CardDeck)

        and:
        def player1 = new Player("player 1")
        def player2 = new Player("player 2")

        and:
        def gameSession = new GameSession(board, cardDeck, [player1, player2])

        and:
        def gameResult1 = [:]
        gameResult1[player1] = []
        gameResult1[player2] = []

        def gameResult2 = [:]
        gameResult2[player1] = []
        gameResult2[player2] = []

        when:
        gameSession.registerGame(gameResult1, 10)
        gameSession.registerGame(gameResult2, 20)

        then:
        gameSession.averageGameDuration == 15

    }

}
