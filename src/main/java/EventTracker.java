import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();
    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        Integer x = tracker.getOrDefault(message, 0);
        tracker.put(message, x+1);
    }

    synchronized public Boolean has(String message) {
//        Integer x = tracker.getOrDefault(message, 0);

        if (tracker.get(message) != null) {
            return tracker.get(message) > 0;
        }
        return false;
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        int x = tracker.get(message);
        tracker.put(message, x-1);
    }

    public Map<String, Integer> getTracker() {
        return tracker;
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }

}
