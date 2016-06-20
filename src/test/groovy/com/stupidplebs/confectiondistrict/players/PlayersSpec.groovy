package com.stupidplebs.confectiondistrict.players

import com.stupidplebs.confectiondistrict.players.Player;
import com.stupidplebs.confectiondistrict.players.Players;

import spock.lang.Specification;

class PlayersSpec extends Specification {
    def "null players parameter should throw NullPointerException"() {
        when:
        new Players(null)

        then:
        NullPointerException e = thrown();
        e.message == "players cannot be null"

    }

    def "empty players parameter should throw IllegalArgumentException"() {
        when:
        new Players([])

        then:
        IllegalArgumentException e = thrown()
        e.message == "players cannot be empty"

    }

    def "getAllPlayers should return list of players as supplied"() {
        given:
        def player1 = new Player("Player 1")
        def player2 = new Player("Player 2")

        and:
        def players = new Players([player1, player2])

        expect:
        players.allPlayers == [player1, player2]

    }

    def "getNextPlayer should return repeating cycle of players in same order"() {
        given:
        def player1 = new Player("Player 1")
        def player2 = new Player("Player 2")

        and:
        def players = new Players([player1, player2])

        expect:
        players.nextPlayer == player1
        players.nextPlayer == player2
        players.nextPlayer == player1
        players.nextPlayer == player2
        players.nextPlayer == player1
        players.nextPlayer == player2

    }

}
