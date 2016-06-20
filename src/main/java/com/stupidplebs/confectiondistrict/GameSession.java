package com.stupidplebs.confectiondistrict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.stupidplebs.confectiondistrict.board.Board;
import com.stupidplebs.confectiondistrict.cards.Card;
import com.stupidplebs.confectiondistrict.cards.deck.CardDeck;
import com.stupidplebs.confectiondistrict.players.Player;

public class GameSession {
    private final Board board;
    private final CardDeck cardDeck;
    private final List<Player> players;

    private final Map<Player, Integer> winners;

    private final List<Map<Player, List<Card>>> gameResults;
    private final List<Long> gameDurations;

    public GameSession(final Board board, final CardDeck cardDeck, final List<Player> players) {
        this.board = board;
        this.cardDeck = cardDeck;

        this.players = new ArrayList<>();
        this.winners = new LinkedHashMap<>();

        for (final Player player : players) {
            this.players.add(player);
            this.winners.put(player, 0);
        }

        this.gameResults = new ArrayList<>();
        this.gameDurations = new ArrayList<>();

    }

    public Board getBoard() {
        return board;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Map<Player, Integer> getWinners() {
        return Collections.unmodifiableMap(winners);
    }

    public void registerGame(final Map<Player, List<Card>> gameResult, final Long gameDuration) {
        gameResults.add(gameResult);
        gameDurations.add(gameDuration);

        final Player winner = getWinner(gameResult);

        winners.put(winner, winners.get(winner) + 1);

    }

    private Player getWinner(final Map<Player, List<Card>> gameResult) {
        Player winner = null;

        for (final Player player : gameResult.keySet()) {
            if (null == winner) {
                winner = player;
            } else if (gameResult.get(player).size() == gameResult.get(winner).size()) {
                winner = player;
            }

        }

        return winner;

    }

    public Integer getLongestGame() {
        Integer longestGame = 0;

        for (final Map<Player, List<Card>> gameOutcome : gameResults) {
            Integer cardsPlayed = getNumberOfCardsPlayed(gameOutcome);
            if (cardsPlayed > longestGame) {
                longestGame = cardsPlayed;
            }

        }

        return longestGame;

    }

    public Integer getShortestGame() {
        Integer shortestGame = Integer.MAX_VALUE;

        for (final Map<Player, List<Card>> gameOutcome : gameResults) {
            Integer cardsPlayed = getNumberOfCardsPlayed(gameOutcome);
            if (cardsPlayed < shortestGame) {
                shortestGame = cardsPlayed;
            }

        }

        return shortestGame;

    }

    public List<Map<Player, List<Card>>> getGames(final Integer numberOfCardsPlayed) {
        final List<Map<Player, List<Card>>> games = new ArrayList<>();

        for (final Map<Player, List<Card>> gameOutcome : gameResults) {
            Integer cardsPlayed = getNumberOfCardsPlayed(gameOutcome);
            if (cardsPlayed == numberOfCardsPlayed) {
                games.add(gameOutcome);
            }

        }

        return games;

    }

    private Integer getNumberOfCardsPlayed(final Map<Player, List<Card>> gameOutcome) {
        Integer numberOfCardsPlayed = 0;

        for (List<Card> cards : gameOutcome.values()) {
            numberOfCardsPlayed += cards.size();
        }

        return numberOfCardsPlayed;

    }

    public Integer getNumberOfGamesPlayed() {
        return gameResults.size();
    }

    public Double getAverageGameDuration() {
        Long totalDuration = 0L;

        for (final Long gameDuration : gameDurations) {
            totalDuration += gameDuration;
        }

        return (double) totalDuration / (double) getNumberOfGamesPlayed();

    }

}
