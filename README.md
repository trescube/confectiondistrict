# Confection District

This simulator is played by the same rules as Candy Land.  It was mainly written for curiosity's sake and to play around with Java 8.  

It basically runs 1,000,000 games for a board/deck configuration and outputs statistics on the number of cards played in the shortest and longest games.

## Interesting Statistics

- As it turns out, the first player (regardless of the number of players) has a small advantage over all other players
- Using the 1949 board, the game can be over in as few as 9 cards pulled
- While there's no theoretical limit to how long as game can last, the longest simulated game has required 746 cards pulled, which, if a card was played every 5 seconds by actual humans, would take 62 minutes to complete

## Configuration

The game is configured by adding a [yaml](http://yaml.org/) file to src/main/resources.  These files determine the number of players, the card deck composition (colors and confections), and the sequence of spaces that make up the game board.  

## Running

To run, enter `gradle run` at the command line.  

Travis status
=============
[![Build Status](https://travis-ci.org/trescube/confectiondistrict.svg?branch=master)](https://travis-ci.org/trescube/confectiondistrict)
