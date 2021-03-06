package poker;

import players.Player;
import players.PlayerObserver;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Sina
 * Date: Mar 5, 2012
 */
public class NotificationManager extends Thread implements Notifiable, PlayerObserver {
    private Notifiable notifiable;
    private PlayerObserver playerObserver;
    private LinkedList<Notification> pendingNotifications = new LinkedList<Notification>();
    private boolean alive = true;
    private long frequency;
    private long notificationValidityDuration;

    public NotificationManager(Notifiable notifiable, PlayerObserver playerObserver, long frequency, long notificationValidityDuration) {
        this.notifiable = notifiable;
        this.playerObserver = playerObserver;
        this.frequency = frequency;
        this.notificationValidityDuration = notificationValidityDuration;
    }

    public void run() {
        try {
            while (alive || pendingNotifications.size() > 0) {
                LinkedList<Notification> notifications = clearPendingNotifications();
                for (Notification notification : notifications) {
                    notification.deliver();
                }
                Thread.sleep(frequency);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private synchronized LinkedList<Notification> clearPendingNotifications() {
        LinkedList<Notification> notifications = new LinkedList<Notification>(pendingNotifications);
        pendingNotifications = new LinkedList<Notification>();
        return notifications;
    }

    private synchronized void addNotification(Notification notification) {
        notification.deliver();
//        pendingNotifications.addLast(notification);
    }

    public void newHand(HandInfo handInfo) {
        Notification notification = new Notification("newHand", new Object[]{handInfo});
        addNotification(notification);
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        Notification notification = new Notification("flopIs", new Object[]{flopCard1, flopCard2, flopCard3});
        addNotification(notification);
    }

    public void turnIs(Card card) {
        Notification notification = new Notification("turnIs", new Object[]{card});
        addNotification(notification);
    }

    public void riverIs(Card card) {
        Notification notification = new Notification("riverIs", new Object[]{card});
        addNotification(notification);
    }

    @Override
    public void handle(Action action) {
        Notification notification = new Notification("handle", new Object[]{action});
        addNotification(notification);
    }

    public void gameEnds() {
        Notification notification = new Notification("gameEnds", new Object[]{});
        addNotification(notification);
        alive = false;
    }

    @Override
    public void cardsShown(Player player, HandType handType) {
        Notification notification = new Notification("cardsShown", new Object[]{player, handType});
        addNotification(notification);
    }

    @Override
    public void cardsMucked(Player player) {
        Notification notification = new Notification("cardsMucked", new Object[]{player});
        addNotification(notification);
    }

    @Override
    public void potWon(List<Player> potWinners, double eachValue) {
        Notification notification = new Notification("potWon", new Object[]{potWinners, eachValue});
        addNotification(notification);
    }

    public void firstCardIs(Player player, Card card) {
        Notification notification = new Notification("firstCardIs", new Object[]{player, card}, PlayerObserver.class, playerObserver);
        addNotification(notification);
    }

    public void secondCardIs(Player player, Card card) {
        Notification notification = new Notification("secondCardIs", new Object[]{player, card}, PlayerObserver.class, playerObserver);
        addNotification(notification);
    }

    private class Notification {
        private String methodName;
        private Object[] arguments;
        private Class destClass;
        private Object destObject;
        private long timestamp;

        public Notification(String methodName, Object[] arguments) {
            this.methodName = methodName;
            this.arguments = arguments;
            destClass = Notifiable.class;
            destObject = notifiable;
            timestamp = System.currentTimeMillis();
        }

        public Notification(String methodName, Object[] arguments, Class destClass, Object destObject) {
            this.methodName = methodName;
            this.arguments = arguments;
            this.destClass = destClass;
            this.destObject = destObject;
            timestamp = System.currentTimeMillis();
        }

        public void deliver() {
            if (System.currentTimeMillis() < timestamp + notificationValidityDuration) {
                for (Method method : destClass.getDeclaredMethods()) {
                    if (method.getName().equals(methodName)) {
                        try {
                            method.invoke(destObject, arguments);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                }
            } else {
                System.out.println(methodName + " dropped because of timeout");
            }
        }
    }
}
