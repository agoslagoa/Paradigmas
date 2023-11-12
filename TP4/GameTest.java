package linea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class GameTest {
    
	@Test public void test01NewGameIsNotFinished() {
        assertEquals(false, new Linea(7, 6, 'C').finished());
    }
	
	@Test public void test02BoardCreatesAccordingToAttributesSpecifiedByUser() {
		Linea game = new Linea(7,6,'C');
		assertEquals( 7, game.getBase() );
		assertEquals( 6, game.getAltura() );
		assertEquals( 'C', game.getVarianteDeTriunfo() );
		System.out.println(game.show());
	}
	
    @Test
    public void test03ShowBoardWithMinimumDimensions() {
        Linea game = new Linea(4, 4, 'C');
        String expectedBoard =
                "|   |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "-----------------\n" +
                "  1   2   3   4 \n" +
                "Current Turn: Red\n";

        assertEquals(expectedBoard, game.show());
    }
	
	@Test public void test04RedAlwaysStartsGameInVariantA() {
		assertEquals( "Red", new Linea(7,6,'A').getTurno() );
	}
	
	@Test public void test05RedAlwaysStartsGameInVariantB() {
		assertEquals( "Red", new Linea(7,6,'B').getTurno() );
	}
	
	@Test public void test06RedAlwaysStartsGameInVariantC() {
		assertEquals( "Red", new Linea(7,6,'C').getTurno() );
	}
	
	@Test public void test07RedPiecePlacedInSelectedColumn() {
	    Linea game = new Linea(7, 6, 'C');
	    game.playRedAt(3);
	    assertTrue(game.isPieceAt(3 - 1, 0, 'R'));
	}
	
	@Test public void test08RedMoveAlternatesToBlueInVariantA() {
		redAlternatesToBlue('A');
	}

	@Test public void test09RedMoveAlternatesToBlueInVariantB() {
		redAlternatesToBlue('B');
	}

	@Test public void test10RedMoveAlternatesToBlueInVariantC() {
	    redAlternatesToBlue('C');
	}
	
	@Test public void test11BlueMoveAlternatesToRedInVariantA() {
	    blueAlternatesToRed('A');
	}

	@Test public void test12BlueMoveAlternatesToRedInVariantB() {
		blueAlternatesToRed('B');
	}

	@Test public void test13BlueMoveAlternatesToRedInVariantC() {
		blueAlternatesToRed('C');
	}
	
	@Test public void test14RedWinsGameHorizontally() {
	    testWinner('A', "Red", 1, 1, 2, 2, 3, 3, 4);
	}

	@Test public void test15RedWinsGameVertically() {
	    testWinner('A', "Red", 1, 2, 1, 2, 1, 2, 1);
	}

	@Test public void test16RedWinsGameDiagonally() {
	    testWinner('B', "Red", 1, 2, 2, 3, 4, 3, 3, 4, 5, 4, 4);
	}

	@Test public void test17BlueWinsGameHorizontally() {
	    testWinner('A', "Blue", 1, 2, 7, 3, 1, 4, 6, 5);
    }

	@Test public void test18BlueWinsGameVertically() {
	    testWinner('A', "Blue", 1, 2, 3, 2, 1, 2, 3, 2);
	}

	@Test public void test19BlueWinsGameDiagonally() {
	    testWinner('B', "Blue", 6, 7, 5, 6, 5, 5, 4, 4, 4, 4);
	}

	@Test public void test20DrawGame() {
	    Linea game = new Linea(4, 4, 'C');
	    playMoves(game, 1, 2, 3, 4, 1, 3, 2, 4, 1, 3, 2, 1, 4, 2, 3, 4);
	    assertGameResult(game, "Draw");
	}
	
	 @Test public void test21DiagonalWinInGameModeAShouldNotFinishGame() {
		 Linea game = new Linea(6, 6, 'A');
		 playMoves(game, 1, 2, 2, 3, 4, 3, 3, 4, 5, 4, 4);
		 assertFalse(game.finished());
	 }
	 
	 @Test public void test22HorizontalWinInGameModeBShouldNotFinishGame() {
		 Linea game = new Linea(6, 6, 'B');
		 playMoves(game, 1, 1, 2, 2, 3, 3, 4);
		 assertFalse(game.finished());
	 }
	 
	 @Test public void test23VerticalWinInGameModeBShouldNotFinishGame() {
		 Linea game = new Linea(6, 6, 'B');
		 playMoves(game, 1, 2, 1, 2, 1, 2, 1);
		 assertFalse(game.finished());
	 }
	
	 @Test public void test24BoardHasToHaveAppropriateDimensions() {
		 assertThrowsLike(() -> new Linea(3, 1, 'A'), Linea.InvalidBoardDimensionsException);
	 }
	
	 @Test public void test25InvalidGameModeThrowsAnException() {
		 assertThrowsLike(() -> new Linea(6, 6, 'X'), GameMode.InvalidTypeOfGameException);
	 }
	 
	 @Test public void test26InvalidColumnExceptionOnNegativeColumn() {
		 assertThrowsLike(() -> new Linea(5, 5, 'C').playRedAt(-1), Linea.invalidColumnException);
	 }
	    
	 @Test public void test27InvalidColumnExceptionOnColumnGreaterThanBase() {
		 assertThrowsLike(() -> new Linea(5, 5, 'C').playRedAt(6), Linea.invalidColumnException);
	 }
	 
	 @Test public void test28CannotPlayBlueWhenItsRedTurn() {
		 assertThrowsLike(() -> new Linea(5, 5, 'C').playBlueAt(1), GameStatus.InvalidBlueMoveException);
	 }
	 
	 @Test public void test29CannotPlayRedWhenItsBlueTurn() {
		 Linea game = new Linea(5, 5, 'C');
		 game.playRedAt(1);
		 assertThrowsLike(() -> game.playRedAt(2), GameStatus.InvalidRedMoveException);
	 }

	 @Test public void test30CannotMakeAMoveWhenGameIsOver() {
		 Linea game = new Linea(6, 6, 'C');
		 playMoves(game, 1, 1, 2, 2, 3, 3, 4);
		 assertTrue(game.finished());
		 assertThrowsLike(() -> game.playBlueAt(6), GameStatus.GameOverException);
	 }

	private void testWinner(char variant, String winner, int... moves) {
	    Linea game = new Linea(7, 6, variant);
	    playMoves(game, moves);
	    assertGameResult(game, winner + " wins the game!");
	}

	private void playMoves(Linea game, int... moves) {
	    Arrays.stream(moves)
	          .forEach(move -> {
	              Runnable playAction = game.getTurno().equals("Red") ? () -> game.playRedAt(move) : () -> game.playBlueAt(move);
	              playAction.run(); });
	}
	
	private void assertGameResult(Linea game, String result) {
	    assertEquals(result, game.getCurrentStatus().getCurrentTurn());
	    assertTrue(game.finished());
	    System.out.println(game.show());
    }
	    
    private void redAlternatesToBlue(char variante) {
	   Linea game = new Linea(7, 6, variante);
	   assertEquals("Red", game.getTurno());
	   game.playRedAt(1);
	   assertEquals("Blue", game.getTurno());
    }
		
	private void blueAlternatesToRed(char variante) {
	   Linea game = new Linea(7, 6, variante);
	   game.playRedAt(1);
	   assertEquals("Blue", game.getTurno());
	   game.playBlueAt(1);
	   assertEquals("Red", game.getTurno());
	}
	
    private void assertThrowsLike(Executable executable, String message) {
	   assertEquals(message, assertThrows(Exception.class, executable).getMessage());
	}
}