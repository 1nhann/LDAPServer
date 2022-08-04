package runnable;

import ldap.LdapServer;

public class RunLDAPServer implements Runnable {
    public static String host = "0.0.0.0";
    public static int port = 9999;

    public static void runLDAPServer(){
        new Thread(new RunLDAPServer()).start();
    }
    public static void runLDAPServer(String host,int port){
        RunLDAPServer.host = host;
        RunLDAPServer.port = port;
        new Thread(new RunLDAPServer()).start();
    }
    public static void runLDAPServer(int port){
        RunLDAPServer.port = port;
        new Thread(new RunLDAPServer()).start();
    }
    @Override
    public void run() {
        try {
            new LdapServer(RunLDAPServer.host,RunLDAPServer.port).run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
