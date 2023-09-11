package queue;

import java.util.ArrayList;

public class NonEmptyQueueState extends QueueState {
    @Override
    public Object head(ArrayList<Object> objects) {
        return objects.get(0);
    }

    @Override
    public Object take(ArrayList<Object> objects) {
    	return objects.remove(0); 
    }

    @Override
    public QueueState stateTransitionOnAdd() {
        return this;
    }

    @Override
    public QueueState stateTransitionOnTake(ArrayList<Object> objects) {
        return QueueStateFactory.getState(objects.isEmpty());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
} 