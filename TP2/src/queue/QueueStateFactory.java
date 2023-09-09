package queue;

import java.util.HashMap;
import java.util.Map;

public class QueueStateFactory {
    private static final Map<Boolean, QueueState> stateInstanceMap = new HashMap<>();
    
    static {
        stateInstanceMap.put(true, new EmptyQueueState());
        stateInstanceMap.put(false, new NonEmptyQueueState());
    }

    public static QueueState getState(boolean isEmpty) {
        return stateInstanceMap.get(isEmpty);
    }
}
