package nemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;

public class SubmarineTest {	
   
	@Test public void test01InitializeNemoAtSurfaceWithDefaultOrientation() {
		assertSubmarineStateAtSpecificCoordinates(new Submarine( new Position(0,0), new NorthOrientation() ), new Position(0,0), 0, "N");
    }
	
	@Test public void test02CreateNemoAtSpecificCoordinatesOtherThan00() {
		assertSubmarineStateAtSpecificCoordinates(new Submarine( new Position(10,8), new EastOrientation() ), new Position(10,8), 0, "E");
	}

	@Test public void test03NemoStaysInPlaceWithoutInstructions() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "", new Position(0,0), 0, "N");
	} 
	
	@Test public void test04RotateRightFromNorth() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "r", "E");
	}

	@Test public void test05RotateRightFromEast() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new EastOrientation() ), "r", "S");
	}
	
	@Test public void test06RotateRightFromSouth() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new SouthOrientation() ), "r", "W");
	}
	
	@Test public void test07RotateRightFromWest() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new WestOrientation() ), "r", "N");
	}
	
	@Test public void test08RotateLeftFromNorth() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "l", "W");
	}
	
	@Test public void test09RotateLeftFromWest() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new WestOrientation() ), "l", "S");
	}
	
	@Test public void test10RotateLeftFromSouth() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new SouthOrientation() ), "l", "E");
	}
	
	@Test public void test11RotateLeftFromEast() {
		checkSubmarineStateAfterRotationInstruction(new Submarine( new Position(0,0), new EastOrientation() ), "l", "N");
	}
	
	@Test public void test12MoveForwardFromNorth() {
		checkSubmarineStateAfterMoveForwardInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), new Position(0,1));
	}
	
	@Test public void test13MoveForwardFromEast() {
		checkSubmarineStateAfterMoveForwardInstruction(new Submarine( new Position(0,0), new EastOrientation() ), new Position(1,0));
	}
	
	@Test public void test14MoveForwardFromSouth() {
		checkSubmarineStateAfterMoveForwardInstruction(new Submarine( new Position(0,0), new SouthOrientation() ), new Position(0,-1));
	}
	
	@Test public void test15MoveForwardFromWest() {
		checkSubmarineStateAfterMoveForwardInstruction(new Submarine( new Position(0,0), new WestOrientation() ), new Position(-1,0));
	}

	@Test public void test16AscendAtSurfaceKeepsDepthAt0() {
		checkSubmarineStateAfterAscendInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), 0); 
	}

	@Test public void test17DescendAtSurface() {
		checkSubmarineStateAfterDescendInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), 1); 
	}
	
	@Test public void test18AscendFromDepth() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		checkSubmarineStateAfterDescendInstruction(nemo, 1); 
		checkSubmarineStateAfterAscendInstruction(nemo, 0); 
	}

	@Test public void test19CommandSequence() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "flfdum", new Position(-1,1), 0, "W");
	}

	@Test public void Test20PerformLargeCommandSequences() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "ffddrffffuumlfflffdd", new Position(2,4), 2, "W");
	}
	
	@Test public void Test21AscendRepeatedlyAtSurfaceDoesNotAffectSubmarine() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine(new Position(0, 0), new NorthOrientation()), "uuuuu", new Position(0,0), 0, "N");
	}
	
	@Test public void Test22DescendExcessivelyDoesNotAffectSubmarine() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine(new Position(0, 0), new NorthOrientation()), "dddddddddddddddddddd", new Position(0,0), 20, "N");
	}
	
	@Test public void test23MaintainDepthWhileMovingForwardInLine() {
		checkSubmarineStateAfterProccesingInstruction(new Submarine( new Position(0,0), new NorthOrientation() ), "fddff", new Position(0,3), 2, "N");
	} 
	
	@Test public void test24ReleasingCapsuleAtSafeDepthDoesNotAffectNemo() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );		
		checkSubmarineStateAfterProccesingInstruction(nemo, "fdfrf", new Position(1,2), 1, "E");
		checkSubmarineStateAfterProccesingInstruction(nemo, "m", new Position(1,2), 1, "E");
	}

	@Test public void test25NemoCanRotate360DegreesRight() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		
		assertEquals("N", nemo.getOrientation());
		checkSubmarineStateAfterProccesingInstruction(nemo, "rrrr", new Position(0,0), 0, "N");
		assertEquals("N", nemo.getOrientation());
	}
	
	@Test public void test26NemoCanRotate360DegreesLeft() {
		Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
		
		assertEquals("N", nemo.getOrientation());
		checkSubmarineStateAfterProccesingInstruction(nemo, "llll", new Position(0,0), 0, "N");
		assertEquals("N", nemo.getOrientation());
	}
	
	@Test public void test27ReleasingCapsuleAtUnsafeDepthDestructsNemo() { 
		assertThrowsLike(()-> new Submarine( new Position(0,0), new NorthOrientation() ).processInstruction("ddm"), NonShootableDepth.ChocolateBrownieException);
	}
	
	@Test public void test28CommandSequenceResultingInNemoDestruction() {
	    Submarine nemo = new Submarine( new Position(0,0), new NorthOrientation() );
	    checkSubmarineStateAfterProccesingInstruction(nemo, "frdf", new Position(1,1), 1, "E");
		assertThrowsLike(()-> nemo.processInstruction("ddm"), NonShootableDepth.ChocolateBrownieException);
	}
	
	private void assertSubmarineStateAtSpecificCoordinates(Submarine nemo, Position expectedPosition, int expectedDepth, String expectedOrientation) {
	    assertEquals(expectedDepth, nemo.getDepth());
	    assertEquals(expectedPosition.getX(), nemo.getPosition().getX());
	    assertEquals(expectedPosition.getY(), nemo.getPosition().getY());
	    assertEquals(expectedOrientation, nemo.getOrientation());
	}
	
	private void checkSubmarineStateAfterRotationInstruction(Submarine nemo, String instruction, String expectedOrientation) {
	    nemo.processInstruction(instruction);
	    assertEquals(expectedOrientation, nemo.getOrientation());
	}
	
	private void checkSubmarineStateAfterAscendInstruction(Submarine nemo, int expectedDepth) {
	    nemo.processInstruction("u");
	    assertEquals(expectedDepth, nemo.getDepth());
	}
	
	private void checkSubmarineStateAfterDescendInstruction(Submarine nemo, int expectedDepth) {
	    nemo.processInstruction("d");
	    assertEquals(expectedDepth, nemo.getDepth());
	}
	
	private void checkSubmarineStateAfterMoveForwardInstruction(Submarine nemo, Position expectedPosition) {
		nemo.processInstruction("f"); 
	    assertEquals(expectedPosition.getX(), nemo.getPosition().getX());
	    assertEquals(expectedPosition.getY(), nemo.getPosition().getY());
	}
	
	private void checkSubmarineStateAfterProccesingInstruction(Submarine nemo, String instruction, Position expectedPosition, int expectedDepth, String expectedOrientation) {
	    nemo.processInstruction(instruction);
	    assertEquals(expectedDepth, nemo.getDepth());
	    assertEquals(expectedPosition.getX(), nemo.getPosition().getX());
	    assertEquals(expectedPosition.getY(), nemo.getPosition().getY());
	    assertEquals(expectedOrientation, nemo.getOrientation());
	}
	
	private void assertThrowsLike( Executable executable, String message ) {
		assertEquals( message, assertThrows( Exception.class, executable ).getMessage() );
	}
	
} 