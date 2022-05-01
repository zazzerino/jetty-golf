DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS person;

-- The 'person' table represents a user.
-- The name 'person' is used because 'user' would conflict with a builtin postgres table.
CREATE TABLE person
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT      NOT NULL,
    session BIGINT    NOT NULL
);

CREATE TABLE game
(
    id          BIGSERIAL PRIMARY KEY,
    deck        TEXT[]    NOT NULL,
    table_cards TEXT[]    NOT NULL,
    players     BIGINT[]  NOT NULL,
    host        BIGINT    NOT NULL,
    state       TEXT      NOT NULL,
    turn        INT       NOT NULL,
    next_player BIGINT    NOT NULL,
    final_turn  BOOLEAN   NOT NULL
);

CREATE TABLE player
(
    game      BIGINT NOT NULL,
    person    BIGINT NOT NULL,
    cards     TEXT[] NOT NULL,
    uncovered INT[]  NOT NULL,
    held_card TEXT,
    PRIMARY KEY (game, person)
);
