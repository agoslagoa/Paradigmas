package queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class QueueTest {

	 private static String Something = "Something";
	 private static String FirstAddedObject = "First";
	 private static String SecondAddedObject = "Second";

	 @Test public void test01QueueShouldBeEmptyWhenCreated() {
	    assertTrue( new Queue().isEmpty() );
	 }

	 @Test public void test02AddElementsToTheQueue() {
	    assertFalse( queueWithSomething().isEmpty() );
	 }
	 @Test public void test03AddedElementsIsAtHead() {
	    assertEquals( Something, queueWithSomething().head() );
	 }

	 @Test public void test04TakeRemovesElementsFromTheQueue() {
	    assertTrue( takeFromQueueWithSomething().isEmpty() );
	 }

	 @Test public void test05TakeReturnsLastAddedObject() {
	    assertEquals( Something , queueWithSomething().take() );
	 }

	 @Test public void test06QueueBehavesFIFO() {
	    Queue queue = queueWithFirstAddedObjectAndSecondAddedObject();
	    assertEquals( queue.take(), FirstAddedObject );
	    assertEquals( queue.take(), SecondAddedObject );
	    assertTrue( queue.isEmpty() );
	 }

	 @Test public void test07HeadReturnsFirstAddedObjectAddedObject() {
	    assertEquals( queueWithFirstAddedObjectAndSecondAddedObject().head(), FirstAddedObject );
	 }

	 @Test public void test08HeadDoesNotRemoveObjectFromQueue() {
	    Queue queue = queueWithSomething(); 
	    assertEquals( 1, queue.size() );
	    queue.head();
	    assertEquals( 1, queue.size() ); 
	 }

	 @Test public void test09SizeRepresentsObjectInTheQueue() {
	    assertEquals( 2, queueWithFirstAddedObjectAndSecondAddedObject().size() );
	 }

	 @Test public void test10CanNotTakeWhenThereAreNoObjectsInTheQueue() {
	    assertEquals( EmptyQueueState.EmptyQueueMessage, assertThrows(Error.class, () -> new Queue().take()).getMessage() );
	 } 

	 @Test public void test09CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
	    assertEquals( EmptyQueueState.EmptyQueueMessage , assertThrows(Error.class, () -> takeFromQueueWithSomething().take()).getMessage());
	 }

	 @Test public void test10CanNotHeadWhenThereAreNoObjectsInTheQueue() {
	    assertEquals(EmptyQueueState.EmptyQueueMessage, assertThrows(Error.class, () -> new Queue().head()).getMessage());
	 }
	  
	 private Queue queueWithSomething() {
	     Queue queue = new Queue();
	     queue.add( Something );
	     return queue;
	 }
	  
	 private Queue queueWithFirstAddedObjectAndSecondAddedObject() {
	     Queue queue = new Queue();
	     queue.add( FirstAddedObject);
	     queue.add( SecondAddedObject);
	     return queue;
	 }
	  
	 private Queue takeFromQueueWithSomething() {
	     Queue queue = queueWithSomething();
	     queue.take();
	     return queue;
	}
} 