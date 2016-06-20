package com.stupidplebs.confectiondistrict

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.board.Board
import com.stupidplebs.confectiondistrict.cards.Card
import com.stupidplebs.confectiondistrict.cards.deck.CardDeck
import com.stupidplebs.confectiondistrict.players.Player

class GameSpec extends Specification {
    def "null gameSession parameter should throw NullPointerException"() {
        when:
        new Game(null)

        then:
        NullPointerException e = thrown()
        e.message == "gameSession cannot be null"

    }

    def "blah"() {
        given:
        def player1 = new Player("player 1")
        def player2 = new Player("player 2")

        and:
        def card1 = Mock(Card)
        def card2 = Mock(Card)
        def card3 = Mock(Card)
        def card4 = Mock(Card)

        def shuffle1 = new LinkedList([card1, card2, card3, card4])
        def shuffle2 = new LinkedList([card4, card3, card2, card1])

        and:
        def board = Mock(Board)

        // no player has finished at space 0
        board.isFinished(0) >> false

        // player 1 moves from 0 to 2
        board.takeTurn(0, card1) >> 2

        // player 1 isn't finished at 2 but moves to 4
        board.isFinished(2) >> false
        board.takeTurn(2, card3) >> 4

        // player 1 isn't finished at 4 but moves to 6
        board.isFinished(4) >> false
        board.takeTurn(4, card4) >> 6

        // player 1 isn't finished at 6 but moves to 10 and is finished at 10
        board.isFinished(6) >> false
        board.isFinished(10) >> true
        board.takeTurn(6, card2) >> 10

        // player 2 moves from 0 to 1
        board.takeTurn(0, card2) >> 1

        // player 2 isn't finished at 1 but moves to 3
        board.isFinished(1) >> false
        board.takeTurn(1, card4) >> 3

        // player 2 isn't finished at 3 but moves to 5
        board.isFinished(3) >> false
        board.takeTurn(3, card3) >> 5

        // player 2 isn't finished at 5 and never gets another card
        board.isFinished(5) >> false
        0 * board.takeTurn(5, card1)

        and:
        def cardDeck = Mock(CardDeck) {
            shuffle() >>> [shuffle1, shuffle2]
        }

        and:
        def players = [player1, player2]

        and:
        def gameSession = new GameSession(board, cardDeck, players)

        and:
        def game = new Game(gameSession)

        when:
        def gameOutcome = game.play()

        then:
        gameOutcome[player1] == [card1, card3, card4, card2]
        gameOutcome[player2] == [card2, card4, card3]

    }

}
