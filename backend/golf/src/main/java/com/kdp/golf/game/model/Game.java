package com.kdp.golf.game.model;

import com.kdp.golf.Lib;
import com.kdp.golf.game.model.card.Card;
import com.kdp.golf.game.model.card.CardLocation;

import java.util.*;
import java.util.stream.Collectors;

public class Game
{
    private final Long id;
    private final Deck deck;
    private final Deque<Card> tableCards;
    private final List<Player> players;
    private Long hostId;
    private Long playerTurn; // index of player in `players`, NOT the player id
    private GameState state;
    private int turn;

    public static final int DECK_COUNT = 2;
    public static final int MAX_PLAYERS = 4;

    public static Game create(Long id, Player host)
    {
        var deck = Deck.create(DECK_COUNT);
        var tableCards = new ArrayDeque<Card>();
        var players = new ArrayList<>(List.of(host));
        var hostId = host.id();
        var nextPlayer = 0L;
        var state = GameState.INIT;
        var turn = 0;

        return new Game(
                id,
                deck,
                tableCards,
                players,
                hostId,
                nextPlayer,
                state,
                turn);
    }

    public Game(Long id,
                Deck deck,
                Deque<Card> tableCards,
                List<Player> players,
                Long hostId,
                Long playerTurn,
                GameState state,
                int turn)
    {
        this.id = id;
        this.deck = deck;
        this.tableCards = tableCards;
        this.players = players;
        this.hostId = hostId;
        this.playerTurn = playerTurn;
        this.state = state;
        this.turn = turn;
    }

    public void addPlayer(Player player)
    {
        if (players.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("attempt to add more than MAX_PLAYERS");
        }

        players.add(player);
    }

    public void removePlayer(Player player)
    {
        players.remove(player);
        var playerId = player.id();

        if (hostId.equals(playerId)) {
            hostId = players.stream().findFirst().orElseThrow().id();
        }

        if (playerTurn.equals(playerId)) {
            nextPlayer();
        }
    }

    public void start()
    {
        deck.shuffle();
        dealStartingHands();
        dealTableCard();
        state = GameState.UNCOVER_TWO;
    }

    private void dealStartingHands()
    {
        for (var i = 0; i < Hand.HAND_SIZE; i++) {
            for (var player : players) {
                var card = deck.deal().orElseThrow();
                player.giveCard(card);
            }
        }
    }

    private void dealTableCard()
    {
        var card = deck.deal().orElseThrow();
        tableCards.push(card);
    }

    private void uncoverTwo(Player player, int handIndex)
    {
        if (player.stillUncoveringTwo()) {
            player.uncoverCard(handIndex);
        } else return;

        var allReady = players.stream().noneMatch(Player::stillUncoveringTwo);
        if (allReady) {
            state = GameState.TAKE;
            ++turn;
        }
    }

    private void uncover(Player player, int handIndex)
    {
        player.uncoverCard(handIndex);
        state = GameState.TAKE;
        ++turn;
        nextPlayer();
    }

    private void takeFromDeck(Player player)
    {
        var card = deck.deal().orElseThrow();
        player.holdCard(card);
        state = GameState.DISCARD;
    }

    private void takeFromTable(Player player)
    {
        var card = tableCards.pop();
        player.holdCard(card);
        state = GameState.DISCARD;
    }

    private void discard(Player player)
    {
        var card = player.discard();
        tableCards.push(card);

        var hasOneCoveredCard = player.uncoveredCardCount() == Hand.HAND_SIZE - 1;
        if (hasOneCoveredCard) {
            state = GameState.TAKE;
            ++turn;
        } else {
            state = GameState.UNCOVER;
        }
    }

    private void swapCard(Player player, int handIndex)
    {
        var card = player.swapCard(handIndex);
        tableCards.push(card);
        ++turn;
        nextPlayer();
    }

    private boolean playerCanAct(Player player)
    {
        var isPlayersTurn = playerTurn.equals(player.id());
        // When the state is UNCOVER_TWO, any player can act.
        var isUncoverTwo = state == GameState.UNCOVER_TWO && player.stillUncoveringTwo();
        return isPlayersTurn || isUncoverTwo;
    }

    private void nextPlayer()
    {
        var player = players.stream()
                .filter(p -> playerTurn.equals(p.id()))
                .findFirst()
                .orElseThrow();

        var index = Lib.findIndex(players, player).orElseThrow();
        playerTurn = (index + 1L) % players.size();
    }

    /**
     * @return the player ids starting from `playerId` clockwise around the table
     */
    public List<Long> playerOrderFrom(Long playerId)
    {
        var playerIds = new ArrayList<>(
                players.stream()
                        .map(Player::id)
                        .toList());

        var index = Lib.findIndex(playerIds, playerId).orElseThrow();
        Collections.rotate(playerIds, -index);
        return playerIds;
    }

    /**
     * @return the card locations that `player` can interact with
     */
    public List<CardLocation> playableCards(Player player) {
        if (!playerCanAct(player)) return Collections.emptyList();

        return switch (state) {
            case UNCOVER_TWO, UNCOVER -> CardLocation.UNCOVER_LOCATIONS;
            case TAKE -> CardLocation.TAKE_LOCATIONS;
            case DISCARD -> CardLocation.DISCARD_LOCATIONS;
            default -> Collections.emptyList();
        };
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return turn == game.turn
                && id.equals(game.id)
                && deck.equals(game.deck)
                && tableCards.equals(game.tableCards)
                && players.equals(game.players)
                && hostId.equals(game.hostId)
                && state == game.state;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, deck, tableCards, players, hostId, playerTurn, state, turn);
    }

    @Override
    public String toString()
    {
        return "Game{" +
                "id=" + id +
                ", deck=" + deck +
                ", tableCards=" + tableCards +
                ", players=" + players +
                ", hostId=" + hostId +
                ", playerTurn=" + playerTurn +
                ", state=" + state +
                ", turn=" + turn +
                '}';
    }
}

//        var builder = new Builder();
//        builder.id = id;
//        builder.deck = Deck.create(DECK_COUNT);
//        builder.tableCards = new ArrayDeque<>();
//        builder.hostId = host.id();
//        builder.state = GameState.INIT;
//        builder.turn = 0;
//        builder.players = new ArrayList<Player>();
//        builder.players.add(host);
//        return builder.build();

//    public static class Builder
//    {
//        public Long id;
//        public Deck deck;
//        public Deque<Card> tableCards;
//        public List<Player> players;
//        public Long hostId;
//        public GameState state;
//        public Integer turn;
//
//        public Game build()
//        {
//            Objects.requireNonNull(id);
//            Objects.requireNonNull(deck);
//            Objects.requireNonNull(tableCards);
//            Objects.requireNonNull(players);
//            Objects.requireNonNull(hostId);
//            Objects.requireNonNull(state);
//            Objects.requireNonNull(turn);
//            return new Game(id, deck, tableCards, players, hostId, state, turn);
//        }
//    }
