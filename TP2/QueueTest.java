package queue;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueueTest {

  private static String QueueIsEmpty = "Queue is empty";
  private static String Second = "Second";
  private static String First = "First";
  private static String Something = "Something";

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
	Queue queue = takeFromQueueWithSomething();
    assertTrue( queue.isEmpty() );
  }

  @Test public void test05TakeReturnsLastAddedObject() {
	Queue queue = queueWithSomething();
    assertEquals( Something , queue.take() );
  }

  @Test public void test06QueueBehavesFIFO() {
	Queue queue = queueWithFirstAndSecond();
    assertEquals( queue.take(), First );
    assertEquals( queue.take(), Second );
    assertTrue( queue.isEmpty() );
  }

  @Test public void test07HeadReturnsFirstAddedObject() {
    assertEquals( queueWithFirstAndSecond().head(), First );
  }

  @Test public void test08HeadDoesNotRemoveObjectFromQueue() {
    assertEquals( 1, queueWithSomething().size() );
    queueWithSomething().head();
    assertEquals( 1, queueWithSomething().size() );
  }

  @Test public void test09SizeRepresentsObjectInTheQueue() {
    assertEquals( 2, queueWithFirstAndSecond().size() );
  }

  @Test public void test10CanNotTakeWhenThereAreNoObjectsInTheQueue() {
    assertEquals( QueueIsEmpty, assertThrows(Error.class, () -> new Queue().take()).getMessage() );
  } 

  @Test public void test09CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
    assertEquals( QueueIsEmpty , assertThrows(Error.class, () -> takeFromQueueWithSomething().take()).getMessage());
  }

  @Test public void test10CanNotHeadWhenThereAreNoObjectsInTheQueue() {
    assertEquals(QueueIsEmpty, assertThrows(Error.class, () -> new Queue().head()).getMessage());
  }
  private Queue queueWithSomething() {
		Queue queue = new Queue();
	    queue.add( Something );
		return queue;
	}
  private Queue queueWithFirstAndSecond() {
		Queue queue = new Queue();
	    queue.add( First);
	    queue.add( Second);
		return queue;
  }
  private Queue takeFromQueueWithSomething() {
		Queue queue = queueWithSomething();
	    queue.take();
		return queue;
	}
}
