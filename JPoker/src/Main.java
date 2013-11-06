import exceptions.OutOfCardsException;
import poker.Dealer;
import processors.SettingDecider;
import processors.StaticSettingDecider;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 5:45:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws OutOfCardsException {
        SettingDecider settingDecider = new StaticSettingDecider();
        Dealer dealer = new Dealer(settingDecider.getGameSetting(), settingDecider.getExternalNotifiables());
        dealer.runGame(2);
    }
}
