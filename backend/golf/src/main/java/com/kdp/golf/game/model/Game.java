package com.kdp.golf.game.model;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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
    private GameState state;
    private int turn;
    private Long nextPlayerId;
    private boolean isFinalTurn;

    public static final int DECK_COUNT = 2;
    public static final int MAX_PLAYERS = 4;

    public static Game create(Long id, Player host)
    {
        var deck = Deck.create(DECK_COUNT);
        var tableCards = new ArrayDeque<Card>();
        var players = Lists.newArrayList(host);
        var state = GameState.INIT;
        var turn = 0;
        var nextPlayerId = host.id();
        var isFinalTurn = false;

        return new Game(id, deck, tableCards, players, host.id(), state, turn, nextPlayerId, isFinalTurn);
    }

    public Game(Long id,
                Deck deck,
                Deque<Card> tableCards,
                List<Player> players,
                Long hostId,
                GameState state,
                int turn,
                Long nextPlayerId,
                boolean isFinalTurn)
    {
        this.id = id;
        this.deck = deck;
        this.tableCards = tableCards;
        this.players = players;
        this.hostId = hostId;
        this.state = state;
        this.turn = turn;
        this.nextPlayerId = nextPlayerId;
        this.isFinalTurn = isFinalTurn;
    }

    public void addPlayer(Player player)
    {
        if (players.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("can't add more than MAX_PLAYERS");
        }

        players.add(player);
    }

    public void removePlayer(Player player)
    {
        players.remove(player);

        if (hostId.equals(player.id())) {
            hostId = players.stream().findFirst().orElseThrow().id();
        }

        if (nextPlayerId.equals(player.id())) {
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

    public void handleEvent(GameEvent event)
    {
        var player = getPlayer(event.playerId()).orElseThrow();
        if (!playerCanAct(player)) return;

        switch (state) {
            case UNCOVER_TWO -> {
                if (event instanceof GameEvent.Uncover e) {
                    uncoverTwo(player, e.handIndex());
                }
            }
            case UNCOVER -> {
                if (event instanceof GameEvent.Uncover e) {
                    uncover(player, e.handIndex());
                }
            }
            case TAKE -> {
                if (event instanceof GameEvent.TakeFromDeck) {
                    takeFromDeck(player);
                } else if (event instanceof GameEvent.TakeFromTable) {
                    takeFromTable(player);
                }
            }
        }
    }

    public Optional<Player> getPlayer(Long playerId)
    {
        return players.stream()
                .filter(p -> p.id().equals(playerId))
                .findFirst();
    }

    private void uncoverTwo(Player player, int handIndex)
    {
        if (player.stillUncoveringTwo()) {
            player.uncoverCard(handIndex);
        } else return;

        var allPlayersReady = players.stream()
                .noneMatch(Player::stillUncoveringTwo);

        if (allPlayersReady) {
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
        // TODO: handle an empty stack
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
        var isPlayersTurn = nextPlayerId.equals(player.id());
        // When the state is UNCOVER_TWO any player can act as long as they have fewer than 2 cards uncovered
        var isUncoverTwo = state == GameState.UNCOVER_TWO && player.stillUncoveringTwo();
        return isPlayersTurn || isUncoverTwo;
    }

    private void nextPlayer()
    {
        var player = getPlayer(nextPlayerId).orElseThrow();
        var index = Lib.findIndex(players, player).orElseThrow();
        var nextIndex = (index + 1) % players.size();
        nextPlayerId = players.get(nextIndex).id();
    }

    /**
     * @return the player ids starting from `playerId` clockwise around the table
     */
    public List<Long> playerOrderFrom(Long playerId)
    {
        var playerIds = players.stream()
                .map(Player::id)
                .collect(Collectors.toCollection(ArrayList::new));

        var index = Lib.findIndex(playerIds, playerId).orElseThrow();
        Collections.rotate(playerIds, -index);
        return playerIds;
    }

    /**
     * @return the card locations that `player` can interact with
     */
    public List<CardLocation> playableCards(Player player)
    {
        if (!playerCanAct(player)) return Collections.emptyList();

        return switch (state) {
            case UNCOVER_TWO, UNCOVER -> CardLocation.UNCOVER_LOCATIONS;
            case TAKE -> CardLocation.TAKE_LOCATIONS;
            case DISCARD -> CardLocation.DISCARD_LOCATIONS;
            default -> Collections.emptyList();
        };
    }

    public Long id()
    {
        return id;
    }

    public Game withId(Long id)
    {
        return new Game(id, deck, tableCards, players, hostId, state, turn, nextPlayerId, isFinalTurn);
    }

    public Deck deck()
    {
        return deck;
    }

    public Deque<Card> tableCards()
    {
        return tableCards;
    }

    public List<Player> players()
    {
        return players;
    }

    public Long hostId()
    {
        return hostId;
    }

    public GameState state()
    {
        return state;
    }

    public int turn()
    {
        return turn;
    }

    public Long nextPlayerId()
    {
        return nextPlayerId;
    }

    public boolean isFinalTurn()
    {
        return isFinalTurn;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game g = (Game) o;
        return turn == g.turn
                && isFinalTurn == g.isFinalTurn
                && id.equals(g.id)
                && deck.equals(g.deck)
                && Iterables.elementsEqual(tableCards, g.tableCards)
                && players.equals(g.players)
                && hostId.equals(g.hostId)
                && state == g.state
                && nextPlayerId.equals(g.nextPlayerId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, deck, tableCards, players, hostId, state, turn, nextPlayerId, isFinalTurn);
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
                ", state=" + state +
                ", turn=" + turn +
                ", nextPlayerId=" + nextPlayerId +
                ", isFinalTurn=" + isFinalTurn +
                '}';
    }
}
