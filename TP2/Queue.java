package queue;

import java.util.ArrayList;

public class Queue {
    private ArrayList<Object> objects = new ArrayList<>();
    private QueueState state = new EmptyQueueState();
    private ArrayList<QueueState> stateMemory = new ArrayList<>();
    
    public Queue add(Object cargo) {
        objects.add(cargo);
        stateMemory.add(state);
        state = new NonEmptyQueueState();
        return this;
    }

    public Object take() {
        Object element = state.take(objects); 
        state = stateMemory.remove(stateMemory.size() -1);
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