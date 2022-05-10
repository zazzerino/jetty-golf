import {Card, CARD_WIDTH} from "./Card";
import {GameState} from "../../types";

interface DeckProps {
  state: GameState;
  playerTurn: number;
}

export function Deck(props: DeckProps) {
  const {state} = props;
  const x = state === "INIT" ? 0 : (-CARD_WIDTH / 2) - 2;
  const y = 0;

  return (
    <Card
      className="Deck"
      cardName="2B"
      x={x}
      y={y}
      // onClick={onClick}
    />
  );
}

// const {userId, gameId, state, playerTurn} = props;
// const onClick = () => onDeckClick(userId, gameId, state, playerTurn);

// function onDeckClick(userId: number, gameId: number, state: GameState, playerTurn: number) {
//   const isUsersTurn = userId === playerTurn;
//   const isTakeState = state === "TAKE";
//
//   if (isUsersTurn && isTakeState) {
//     sendTakeFromDeck(userId, gameId);
//   }
// }
