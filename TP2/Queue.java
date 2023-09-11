package queue;

import java.util.ArrayList;

public class Queue {
    private ArrayList<Object> objects = new ArrayList<>();
    private QueueState state = new EmptyQueueState();

    public Queue add(Object cargo) {
        objects.add(cargo);
        state = state.stateTransitionOnAdd();
        return this;
    }

    public Object take() {
        Object element = state.take(objects);
        state = state.stateTransitionOnTake(objects); 
        return element;
    }

    public Object head() {
        return state.head(objects);
    }

    public int size() {
        return objects.size();
    }

    public boolean isEmpty() {
        return state.isEmpty();
    }
}