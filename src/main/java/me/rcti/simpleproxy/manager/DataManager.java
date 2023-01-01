package me.rcti.simpleproxy.manager;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;

public class DataManager {
    public DataManager() {
        Main.getInstance().data = this;
    }

    public String prefix = CC.translate("&f&lSIMPLEPROXY &8/ &7");
    public String partyprefix = CC.translate("&f&lPARTY &8/ &7");
    public String partychat = CC.translate("&f&lPARTYCHAT &8/ &7");

    public String unknown(String cmd) {
        return this.prefix + CC.translate("&fUnknown command.");
    }
}
