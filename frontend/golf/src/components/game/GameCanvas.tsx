import {Game} from "../../types";
import {Deck} from "./Deck";

export function GameCanvas(props: {userId: number; game: Game}) {
  const {game} = props;

  const width = 600;
  const height = 500;
  const viewBox = `${-width/2} ${-height/2} ${width} ${height}`;

  return (
    <svg
      className="GameCanvas"
      width={width}
      height={height}
      viewBox={viewBox}
    >
      <Deck state="INIT" playerTurn={game.nextPlayerId} />
    </svg>
  );
}
