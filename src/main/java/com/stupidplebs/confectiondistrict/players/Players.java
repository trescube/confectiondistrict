package com.stupidplebs.confectiondistrict.players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Players {
	private final List<Player> allPlayers;
	private final Queue<Player> playerQueue;
	
	public Players(final List<Player> players) {
		if (null == players) {
			throw new NullPointerException("players cannot be null");
		}
		if (players.isEmpty()) {
			throw new IllegalArgumentException("players cannot be empty");
		}
		
		this.allPlayers = new ArrayList<>(players);
		this.playerQueue = new LinkedList<>();
		
	}
	
	public Player getNextPlayer() {
		if (playerQueue.isEmpty()) {
			playerQueue.addAll(allPlayers);
		}
		
		return playerQueue.remove();
		
	}
	
	public List<Player> getAllPlayers() {
		return allPlayers;
	}
	
}
