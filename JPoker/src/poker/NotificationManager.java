package poker;

import players.Player;
import players.PlayerObserver;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 5, 2012
 * Time: 6:19:28 PM
 * To change this template use File | Settings | File Templates.
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
        pendingNotifications.addLast(notification);
    }

    public void newHand(GameSetting gameSetting) {
        addNotification(new Notification("newHand", new Object[] {gameSetting}));
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        addNotification(new Notification("flopIs", new Object[] {flopCard1, flopCard2, flopCard3}));
    }

    public void turnIs(Card card) {
        addNotification(new Notification("turnIs", new Object[] {card}));
    }

    public void riverIs(Card card) {
        addNotification(new Notification("riverIs", new Object[] {card}));
    }

    public void playerFolded(Player foldingPlayer) {
        addNotification(new Notification("playerFolded", new Object[] {foldingPlayer}));
    }

    public void playerBet(Player bettingPlayer, double bet) {
        addNotification(new Notification("playerBet", new Object[] {bettingPlayer, bet}));
    }

    public void showDown(ShowDown showDown) {
        addNotification(new Notification("showDown", new Object[] {showDown}));
    }

    public void gameEnds() {
        addNotification(new Notification("gameEnds", new Object[] {}));
        alive = false;
    }

    public void firstCardIs(Player player, Card card) {
        addNotification(new Notification("firstCardIs", new Object[] {player, card}, PlayerObserver.class, playerObserver));
    }

    public void secondCardIs(Player player, Card card) {
        addNotification(new Notification("secondCardIs", new Object[] {player, card}, PlayerObserver.class, playerObserver));
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
