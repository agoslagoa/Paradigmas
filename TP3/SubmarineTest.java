package nemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SubmarineTest {

    
	@Test public void test01InitializeNemoAtSurfaceWithDefaultOrientation() {
        // Creamos una instancia de Nemo
        Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );

        // Verificamos que el submarino comienza en la superficie (profundidad 0)
        assertEquals( 0, nemo.getDepth() );

        // Verificamos la posición inicial en el sistema cartesiano (x, y)
        assertEquals( 0, nemo.getPosition().getX() );
        assertEquals( 0, nemo.getPosition().getY() );

        // Verificamos que el submarino comienza mirando hacia el norte
        assertEquals("N", nemo.getOrientation());
    }
	
	@Test public void test02CreateNemoAtSpecificCoordinatesOtherThan00() {
		Submarine nemo = new Submarine( new Position(10,8), new EastOrientation() );
		
		// Verificamos que el submarino comienza en la superficie (profundidad 0)
        assertEquals( 0, nemo.getDepth() );

        // Verificamos la posición inicial en el sistema cartesiano (x, y)
        assertEquals( 10, nemo.getPosition().getX() );
        assertEquals( 8, nemo.getPosition().getY() );

        // Verificamos que el submarino comienza mirando hacia el este
        assertEquals("E", nemo.getOrientation());
	} 
    
	@Test public void test03NemoDoesNotMoveWithoutReceivingInstructions() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    String emptyInstruction = "";
	    nemo.processInstruction(emptyInstruction); // Llamamos al método para procesar la instrucción
	    
        assertEquals( 0, nemo.getDepth());
		assertEquals( 0, nemo.getPosition().getX() );
		assertEquals( 0, nemo.getPosition().getY() );
	} 
	
	@Test public void test04RotateRightFromNorth() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("r"); // Girar a la derecha
	    assertEquals("E", nemo.getOrientation()); // Debe estar mirando hacia el este
	}
	
	@Test public void test05RotateRightFromEast() {
		Submarine nemo = new Submarine( new Position(0,0), new EastOrientation() );
	    nemo.processInstruction("r"); // Girar a la derecha
	    assertEquals("S", nemo.getOrientation()); // Debe estar mirando hacia el sur
	}
	
	@Test public void test06RotateRightFromSouth() {
		Submarine nemo = new Submarine( new Position(0,0), new SouthOrientation() );
	    nemo.processInstruction("r"); // Girar a la derecha
	    assertEquals("W", nemo.getOrientation()); // Debe estar mirando hacia el oeste
	}
	
	@Test public void test07RotateRightFromWest() {
		Submarine nemo = new Submarine( new Position(0,0), new WestOrientation() );
	    nemo.processInstruction("r"); // Girar a la derecha
	    assertEquals("N", nemo.getOrientation()); // Debe estar mirando hacia el norte
	}
	
	@Test public void test08RotateLeftFromNorth() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("l"); // Girar a la izquuerda
	    assertEquals("W", nemo.getOrientation()); // Debe estar mirando hacia el oeste
	}
	
	@Test public void test09RotateLeftFromWest() {
		Submarine nemo = new Submarine( new Position(0,0), new WestOrientation() );
	    nemo.processInstruction("l"); // Girar a la izquierda
	    assertEquals("S", nemo.getOrientation()); // Debe estar mirando hacia el sur
	}
	
	@Test public void test10RotateLeftFromSouth() {
		Submarine nemo = new Submarine( new Position(0,0), new SouthOrientation() );
	    nemo.processInstruction("l"); // Girar a la izquierda
	    assertEquals("E", nemo.getOrientation()); // Debe estar mirando hacia el este
	}
	
	@Test public void test11RotateLeftFromEast() {
		Submarine nemo = new Submarine( new Position(0,0), new EastOrientation() );
	    nemo.processInstruction("l"); // Girar a la izquierda
	    assertEquals("N", nemo.getOrientation()); // Debe estar mirando hacia el norte
	}
	
	@Test public void test12MoveForwardFromNorth() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("f"); // Mover hacia adelante (norte)
	    assertEquals(1, nemo.getPosition().getY());
	}
	
	@Test public void test13MoveForwardFromEast() {
		Submarine nemo = new Submarine( new Position(0,0), new EastOrientation() );
	    nemo.processInstruction("f"); // Mover hacia adelante (norte)
	    assertEquals(1, nemo.getPosition().getX());
	}
	
	@Test public void test14MoveForwardFromSouth() {
		Submarine nemo = new Submarine( new Position(0,0), new SouthOrientation() );
	    nemo.processInstruction("f"); // Mover hacia adelante (norte)
	    assertEquals(-1, nemo.getPosition().getY());
	}
	
	@Test public void test15MoveForwardFromWest() {
		Submarine nemo = new Submarine( new Position(0,0), new WestOrientation() );
	    nemo.processInstruction("f"); // Mover hacia adelante (norte)
	    assertEquals(-1, nemo.getPosition().getX());
	}
	
	@Test public void test16AssertAscendAtSurfaceShouldKeepDepthAt0() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("u"); // Intento de ascender desde la superficie
	    assertEquals(0, nemo.getDepth()); // Debe permanecer en la superficie (profundidad 0)
	}
	
	@Test public void test17AssertDescendAtSurface() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("d"); 
	    assertEquals(1, nemo.getDepth()); 
	}
	
	@Test public void test18AssertAscendFromDepth() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    nemo.processInstruction("d"); 
	    assertEquals(1, nemo.getDepth()); 
	    
	    nemo.processInstruction("u"); 
	    assertEquals(0, nemo.getDepth()); 
	}
	
	@Test public void test19AssertCommandSequence() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );

	    nemo.processInstruction("flfdum");

	    // Verificar el estado final del submarino después de ejecutar la secuencia de comandos
	    assertEquals(1, nemo.getPosition().getY());         // Debe estar en la posición Y = 1
	    assertEquals("W", nemo.getOrientation()); // Debe estar mirando hacia el oeste
	    assertEquals(-1, nemo.getPosition().getX());         // Debe estar en la posición X = -1
	    assertEquals(0, nemo.getDepth());     // Debe ascender (profundidad 0)
	}
	
	@Test public void Test20PerformingLargeCommandSequences() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );

	    nemo.processInstruction("ffddrffffuumlfflffdd");
	    
	    assertEquals(2, nemo.getDepth());
		assertEquals(2, nemo.getPosition().getX());
	    assertEquals(4, nemo.getPosition().getY());
		assertEquals("W", nemo.getOrientation());
	}
	
	@Test public void Test21AscendRepeatedlyAtSurfaceDoesNotAffectSubmarine() {
		Submarine nemo = new Submarine(new Position(0, 0), new NorthOrientation());

	    nemo.processInstruction("uuuuu");

	    assertEquals(0, nemo.getDepth());
	}
	
	@Test public void Test22DescendExcessivelyDoesNotAffectSubmarine() {
		Submarine nemo = new Submarine(new Position(0, 0), new NorthOrientation());

	    nemo.processInstruction("dddddddddddddddddddd");
	    
	    assertEquals(20, nemo.getDepth());
	}
	
	@Test public void test23MaintainDepthWhileMovingForwardInLine() {
	    Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    
	    nemo.processInstruction("fddff");
	    assertEquals(3, nemo.getPosition().getY());
	    assertEquals(2, nemo.getDepth());
	} 
	
	@Test public void test24ReleasingCapsuleAtSafeDepthDoesNotAffectNemo() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		
		nemo.processInstruction("fdfrf");
		
		assertEquals(1, nemo.getDepth());
		assertEquals(1, nemo.getPosition().getX());
		assertEquals(2, nemo.getPosition().getY());
		assertEquals("E", nemo.getOrientation());
		
		nemo.processInstruction("m");
		
		assertEquals(1, nemo.getDepth());
		assertEquals(2, nemo.getPosition().getY());
		assertEquals(1, nemo.getPosition().getX());
		assertEquals("E", nemo.getOrientation());
	}
	
	@Test public void test25NemoCanRotate360DegreesRight() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		
		assertEquals("N", nemo.getOrientation());
		nemo.processInstruction("rrrr");
		assertEquals("N", nemo.getOrientation());
		
	}
	
	@Test public void test26NemoCanRotate360DegreesLeft() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		
		assertEquals("N", nemo.getOrientation());
		nemo.processInstruction("llll");
		assertEquals("N", nemo.getOrientation());
	}
	
	@Test public void test27ReleasingCapsuleAtUnsafeDepthDestructsNemo() { 
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );

	    // Descender a profundidad 2 o mayor ('ddm') y liberar la cápsula hace que Nemo explote
	    assertThrows(IllegalStateException.class, () -> {
	        nemo.processInstruction("ddm");
	    });
	}
	
	@Test public void test28AssertCommandSequenceResultingInNemoDestruction() {
//		NemoPosition initialPosition = new NemoPosition(0, 0);
	    Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    
	    nemo.processInstruction("frdf");
	    assertEquals("E", nemo.getOrientation());
	    assertEquals(1, nemo.getPosition().getX());
		assertEquals(1, nemo.getPosition().getY());

	    // Ejecutar una secuencia de comandos que intenta liberar la cápsula a profundidad 3 o mayor 
	    assertThrows(IllegalStateException.class, () -> {
	        nemo.processInstruction("ddm");
	    });
	} 
} 