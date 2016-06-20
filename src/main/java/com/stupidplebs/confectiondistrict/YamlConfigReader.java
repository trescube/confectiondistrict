package com.stupidplebs.confectiondistrict;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.stupidplebs.confectiondistrict.board.ActualBoard;
import com.stupidplebs.confectiondistrict.board.Board;
import com.stupidplebs.confectiondistrict.cards.deck.ActualCardDeck;
import com.stupidplebs.confectiondistrict.cards.deck.CardDeck;
import com.stupidplebs.confectiondistrict.players.Player;

public class YamlConfigReader {
	private final Yaml yaml = new Yaml();
	
	public YamlConfigReader() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GameSession createGameSession(final InputStream inputStream) {
		final Map map = (Map)yaml.load(inputStream);
		
		final List<Player> players = loadPlayers((List<String>)map.get("players"));
		final CardDeck cardDeck = loadCardDeck((Map<String,List>)map.get("deck"));
		final Board board = loadBoard((List<Map<String,Object>>)map.get("spaces"));
		
		return new GameSession(board, cardDeck, players);
		
	}
	
	private List<Player> loadPlayers(final List<String> configPlayers) {
		final List<Player> players = new ArrayList<>();
		
		for (final String player : configPlayers) {
			players.add(new Player(player));
		}
		
		return players;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CardDeck loadCardDeck(final Map<String,List> deck) {
		final ActualCardDeck.Builder builder = new ActualCardDeck.Builder();

		for (final Map<String,Object> colorCard : (List<Map<String,Object>>)deck.get("colors")) {
			final Color color = Color.valueOf((String)colorCard.get("color"));
			final Integer singleQuantity = (Integer)colorCard.get("single");
			final Integer doubleQuantity = (Integer)colorCard.get("double");
			
			if (singleQuantity > 0) {
				builder.singleMoveCard(color, singleQuantity);
			}
			if (doubleQuantity > 0) {
				builder.doubleMoveCard(color, doubleQuantity);
			}
			
		}
		
		for (final String confectionCard : (List<String>)deck.get("confections")) {
			builder.confectionCard(Confection.valueOf(confectionCard));
		}
		
		return builder.build();
	}	

	private Board loadBoard(final List<Map<String,Object>> spaces) {
		final ActualBoard.Builder builder = new ActualBoard.Builder();
		
		for (final Map<String,Object> space: spaces) {
			if (isConfection(space)) {
				final Confection confection = Confection.valueOf((String)space.get("confection"));
				builder.confection(confection);
				
			} else {
				final Color color = Color.valueOf((String)space.get("color"));
				
				if (isTrailhead(space)) {
					final Integer jump = (Integer)space.get("jump");
					builder.trailhead(color, jump);
				}
				else if (isSticky(space)) {
					builder.sticky(color);
				} 
				else {
					builder.color(color);
				}
				
			}
			
		}
		
		return builder.build();
		
	}

	private Boolean isConfection(final Map<String,Object> space) {
		return space.containsKey("confection");
	}
	
	private Boolean isTrailhead(final Map<String,Object> space) {
		return space.containsKey("jump");
	}
	
	private Boolean isSticky(final Map<String,Object> space) {
		return space.containsKey("sticky") && (Boolean)space.get("sticky");
	}
	
}
