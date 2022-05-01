package com.kdp.golf.game.model;

public enum GameState
{
    INIT, // the game hasn't started yet
    UNCOVER_TWO, // each player uncovers two cards (in any order)
    TAKE, // a player needs to take a card
    DISCARD, // a player needs to discard a card
    UNCOVER, // a player needs to uncover a card in their hand
    GAME_OVER, // a victor has emerged
}
