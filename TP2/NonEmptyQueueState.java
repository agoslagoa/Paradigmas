package queue;

import java.util.ArrayList;

public class NonEmptyQueueState extends QueueState {
    @Override
    public Object head(ArrayList<Object> objects) {
        return objects.get(0);
    }

    @Override
    public Object take(ArrayList<Object> objects) {
        Object firstElement = objects.get(0);
        objects.remove(0);
        return firstElement;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
