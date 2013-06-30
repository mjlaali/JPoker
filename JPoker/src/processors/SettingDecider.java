package processors;

import poker.GameSetting;
import poker.Notifiable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 6:21:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SettingDecider {
    GameSetting getGameSetting();
    List<Notifiable> getExternalNotifiables();
}
