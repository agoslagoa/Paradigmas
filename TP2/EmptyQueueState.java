package queue;

import java.util.ArrayList;

public class EmptyQueueState extends QueueState {
	
    public static final String EmptyQueueMessage = "Queue is empty"; 
	
    public Object head(ArrayList<Object> objects) { throw new Error(EmptyQueueMessage); }

    public Object take(ArrayList<Object> objects) { throw new Error(EmptyQueueMessage); }

    public boolean isEmpty() { return true; } 
}