package com.stupidplebs.confectiondistrict;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.stupidplebs.confectiondistrict.cards.Card;
import com.stupidplebs.confectiondistrict.players.Player;

public class GameDriver {
	public static void main(String[] args) {
		try {
			final Integer numberOfGames = 1_000_000;
			final String filename = "src/main/resources/1949.yaml";
			
			YamlConfigReader yamlConfigReader = new YamlConfigReader();
			
			final GameSession gameSession = yamlConfigReader.createGameSession(
					new FileInputStream(new File(filename)));
			
			new GameDriver().run(numberOfGames, gameSession);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public GameDriver() {}
	
	public void run(final Integer numberOfGames, final GameSession gameSession) {
		final Game game = new Game(gameSession);
		
		for (int i = 0; i < numberOfGames; i++) {
			final Long start = System.currentTimeMillis();
			final Map<Player,List<Card>> cardsPlayed = game.play();
			final Long finish = System.currentTimeMillis();
			
			gameSession.registerGame(cardsPlayed, finish-start);
			
		}
		
		final Map<Player,Integer> winners = gameSession.getWinners();
		
		System.out.println(numberOfGames + " games played");
		for (final Player player : winners.keySet()) {
			System.out.println(player + ": " + winners.get(player) + " (" + (((double)winners.get(player)/(double)numberOfGames))*100.0 + "%)");
		}
		
		final Integer longestGame = gameSession.getLongestGame();
		final Integer shortestGame = gameSession.getShortestGame();
		
		System.out.println("longest game:  " + longestGame + " cards played");
		System.out.println("shortest game: " + shortestGame + " cards played");
		System.out.println("avg game duration: " + gameSession.getAverageGameDuration() + "ms");
		
//		for (final Map<Player,List<Card>> gameOutcome : gameSession.getGames(10)) {
//			for (final Player player : gameOutcome.keySet()) {
//				System.out.println(player + ": " + gameOutcome.get(player));
//				
//			}
//			System.out.println();
//			
//		}
		
	}
	
}
