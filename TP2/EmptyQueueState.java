package queue;

import java.util.ArrayList;

public class EmptyQueueState extends QueueState {
    @Override
    public Object head(ArrayList<Object> objects) {
        throw new Error("Queue is empty");
    }

    @Override
    public Object take(ArrayList<Object> objects) {
        throw new Error("Queue is empty");
    }

    @Override
    public QueueState stateTransitionOnAdd() {
        return new NonEmptyQueueState();
    }

    @Override
    public QueueState stateTransitionOnTake(ArrayList<Object> objects) { 
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}