import exceptions.OutOfCardsException;
import poker.Dealer;
import processors.SettingDecider;
import processors.StaticSettingDecider;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class Main {
    public static void main(String[] args) throws OutOfCardsException {
        SettingDecider settingDecider = new StaticSettingDecider();
        Dealer dealer = new Dealer(settingDecider.getGameSetting(), settingDecider.getExternalNotifiables());
        dealer.runGame(4);
    }
}
