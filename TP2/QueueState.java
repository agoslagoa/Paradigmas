package queue;

import java.util.ArrayList;

public abstract class QueueState {
	
    public abstract Object head(ArrayList<Object> objects);

    public abstract Object take(ArrayList<Object> objects);

    public abstract boolean isEmpty();
}