package queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class QueueTest {

	 @Test public void test01QueueShouldBeEmptyWhenCreated() {
	    assertTrue( new Queue().isEmpty() );
	 }

	 @Test public void test02AddElementsToTheQueue() {
	    assertFalse( queueWithSomething().isEmpty() );
	 }
	 @Test public void test03AddedElementsIsAtHead() {
	    assertEquals( "Something" , queueWithSomething().head() );
	 }

	 @Test public void test04TakeRemovesElementsFromTheQueue() {
	    assertTrue( takeFromQueueWithSomething().isEmpty() );
	 }

	 @Test public void test05TakeReturnsLastAddedObject() {
	    assertEquals( "Something" , queueWithSomething().take() );
	 }

	 @Test public void test06QueueBehavesFIFO() {
	    Queue queue = queueWithFirstAndSecondObjects();
	    assertEquals( queue.take(), "First" );
	    assertEquals( queue.take(), "Second" );
	    assertTrue( queue.isEmpty() );
	 }

	 @Test public void test07HeadReturnsFirstAddedObjectAddedObject() {
	    assertEquals( queueWithFirstAndSecondObjects().head(), "First" );
	 }

	 @Test public void test08HeadDoesNotRemoveObjectFromQueue() {
	    Queue queue = queueWithSomething(); 
	    assertEquals( 1, queue.size() );
	    queue.head();
	    assertEquals( 1, queue.size() ); 
	 }

	 @Test public void test09SizeRepresentsObjectInTheQueue() {
	    assertEquals( 2, queueWithFirstAndSecondObjects().size() );
	 }

	 @Test public void test10CanNotTakeWhenThereAreNoObjectsInTheQueue() {
	    assertThrowsLike(() -> new Queue().take(), EmptyQueueState.EmptyQueueMessage);
	 } 

	 @Test public void test09CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
	    assertThrowsLike(() -> takeFromQueueWithSomething().take(), EmptyQueueState.EmptyQueueMessage);
	 }

	 @Test public void test10CanNotHeadWhenThereAreNoObjectsInTheQueue() {
	    assertThrowsLike(() -> new Queue().head(), EmptyQueueState.EmptyQueueMessage);
	 }
	  
	 private Queue queueWithSomething() {
	     Queue queue = new Queue();
	     queue.add( "Something" );
	     return queue;
	 }
	  
	 private Queue queueWithFirstAndSecondObjects() { 
	     Queue queue = new Queue();
	     queue.add( "First");
	     queue.add( "Second");
	     return queue;
	 }
	  
	 private Queue takeFromQueueWithSomething() {
	     Queue queue = queueWithSomething();
	     queue.take();
	     return queue;
	}
	 
	 private void assertThrowsLike( Executable e, String message ) {
		 assertEquals( message, assertThrows( Error.class, e ).getMessage() );
	}
} 
