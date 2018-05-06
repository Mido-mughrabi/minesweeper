import java.util.Scanner;

import controller.Controller;
import model.Game;
import model.Minefield;
import view.GameView;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Game game = new Game();
		Scanner s = new Scanner(System.in);
		game.getMineField().printUncensoredField();
		game.getMineField().print();
		while(!game.isGameOver() && !game.isWin())
		{
			int x = s.nextInt();
			int y = s.nextInt();
			game.uncover(x, y);
			game.getMineField().print();
		}*/
		Game game = new Game();
		GameView gameView = new GameView(game.getMineField().getField());
		Controller controller = new Controller(game, gameView);
	}

}
