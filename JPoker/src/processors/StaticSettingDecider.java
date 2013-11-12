package processors;

import players.CheckingPlayer;
import players.PlayerObserver;
import poker.GameSetting;
import poker.Notifiable;
import poker.NotificationManager;
import ui.GraphicsUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 11:34:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticSettingDecider implements SettingDecider {
    List<Notifiable> externalNotifiables = new ArrayList<Notifiable>();

    public GameSetting getGameSetting() {
        GraphicsUserInterface gui = new GraphicsUserInterface();
        gui.setVisible(true);

//        Analyzer analyzer = new Analyzer();
//        externalNotifiables.add(new ConsoleUI(false, false, true, analyzer));
//        externalNotifiables.add(analyzer);
//        NotificationManager manager = new NotificationManager(gui, gui, 100, 30000);
//        manager.start();
        externalNotifiables.add(gui);
        GameSetting gameSetting = new GameSetting(500, 1000);

        List<PlayerObserver> playerObservers = new ArrayList<PlayerObserver>();
        playerObservers.add(gui);
        for (int i = 0; i < 8; i++) {
            gameSetting.addPlayerStack(new CheckingPlayer("Player " + ((i + 1) * 111)), (i+1) * 1000, playerObservers);
        }
        return gameSetting;
    }

    public List<Notifiable> getExternalNotifiables() {
        return externalNotifiables;
    }
}
