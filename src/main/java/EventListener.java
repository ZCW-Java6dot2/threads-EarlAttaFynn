public class EventListener extends Thread {

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run() {
        EventHandler e = () -> System.out.println(this.messageToReplyWith);

        while (!readyToQuit()) {
            if (shouldReply()) {
                eventTracker.handle(this.messageToListenFor, e);
            }
        }
    }

    public Boolean readyToQuit() {
        return this.eventTracker.has("quit");

    }

    public Boolean shouldReply() {
        return this.eventTracker.has(this.messageToListenFor);
    }

    public void reply() {
        EventHandler e = () -> System.out.println(this.messageToReplyWith);
        this.eventTracker.handle(this.messageToListenFor, e);
    }
}