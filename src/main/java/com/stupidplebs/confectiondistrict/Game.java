package com.stupidplebs.confectiondistrict;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.stupidplebs.confectiondistrict.board.Board;
import com.stupidplebs.confectiondistrict.cards.Card;
import com.stupidplebs.confectiondistrict.cards.deck.CardDeck;
import com.stupidplebs.confectiondistrict.players.Player;
import com.stupidplebs.confectiondistrict.players.Players;

public class Game {
	private final Board board;
	private final CardDeck cardDeck;
	private final List<Player> players;

	public Game(final GameSession gameSession) {
		if (null == gameSession) {
			throw new NullPointerException("gameSession cannot be null");
		}
		
		this.board = gameSession.getBoard();
		this.cardDeck = gameSession.getCardDeck();
		this.players = gameSession.getPlayers();
		
	}

	public Map<Player,List<Card>> play() {
		// store as in-order LinkedHashMap so debugging is easier
		final Map<Player,List<Card>> cardsPlayed = new LinkedHashMap<>();
		final Map<Player,Integer> playerLocations = new LinkedHashMap<>();
		
		Queue<Card> remainingCards = cardDeck.shuffle();
		
		final Players playersQueue = new Players(players);

		// initialize a list of cards for each player and put all players at the start space
		for (final Player player : playersQueue.getAllPlayers()) {
			cardsPlayed.put(player, new ArrayList<>());
			playerLocations.put(player, 0);
		}
		
		while (!anyPlayerIsFinished(playerLocations)) {
			// shuffle deck if there are no cards left
			if (remainingCards.isEmpty()) {
				remainingCards = cardDeck.shuffle();
			}
			
			final Player player = playersQueue.getNextPlayer();
			final Card card = remainingCards.remove();

			cardsPlayed.get(player).add(card);

			final Integer currentSpace = playerLocations.get(player);
			final Integer newSpace = board.takeTurn(currentSpace, card);
			
			playerLocations.put(player, newSpace);
		}
		
		return cardsPlayed;
		
	}
	
	private Boolean anyPlayerIsFinished(final Map<Player,Integer> playerLocations) {
		for (final Player player : playerLocations.keySet()) {
			if (board.isFinished(playerLocations.get(player))) {
				return true;
			}
		}
		return false;
	}

}
