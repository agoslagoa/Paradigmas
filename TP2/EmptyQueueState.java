package queue;

import java.util.ArrayList;

public class EmptyQueueState extends QueueState {
	
    public static final String queueIsEmpty = "Queue is empty";
	
    public Object head(ArrayList<Object> objects) { throw new Error(queueIsEmpty); }

    public Object take(ArrayList<Object> objects) { throw new Error(queueIsEmpty); }

    public boolean isEmpty() { return true; } 
}
