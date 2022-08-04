package ldap;

import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.ldif.LdifEntry;
import org.apache.directory.api.ldap.model.ldif.LdifReader;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.IOUtils;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.partition.impl.avl.AvlPartition;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;


public class LdapServer {

    private static final String DEFAULT_LDIF_FILENAME = "fuck.ldif";

    private DirectoryService directoryService;
    private org.apache.directory.server.ldap.LdapServer ldapServer;
    private String host = "0.0.0.0";
    private int port = 9999;

    public LdapServer(){

    }
    public LdapServer(String host){
        this.host = host;
    }
    public LdapServer(int port){
        this.port = port;
    }
    public LdapServer(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        long startTime = System.currentTimeMillis();

        InMemoryDirectoryServiceFactory dsFactory = new InMemoryDirectoryServiceFactory();
        dsFactory.init("ds");

        directoryService = dsFactory.getDirectoryService();
        System.out.println("Directory service started in " + (System.currentTimeMillis() - startTime) + "ms");
        directoryService.setAllowAnonymousAccess(true);
        importLdif();

        ldapServer = new org.apache.directory.server.ldap.LdapServer();
        TcpTransport tcp = new TcpTransport(this.host, this.port);
        ldapServer.setTransports(tcp);

        ldapServer.setDirectoryService(directoryService);

        ldapServer.start();

        System.out.println("You can connect to the server now");
        System.out.println("URL:      ldap://" + formatPossibleIpv6(this.host) + ":" + this.port);
        System.out.println("LDAP server started in " + (System.currentTimeMillis() - startTime) + "ms");
    }


    private void importLdif() throws Exception {
        importLdif(new LdifReader(LdapServer.class.getResourceAsStream("/" + DEFAULT_LDIF_FILENAME)));
    }

    private void importLdif(LdifReader ldifReader) throws Exception {
        try {
            for (LdifEntry ldifEntry : ldifReader) {
                checkPartition(ldifEntry);
                System.out.print(ldifEntry.toString());
                directoryService.getAdminSession()
                        .add(new DefaultEntry(directoryService.getSchemaManager(), ldifEntry.getEntry()));
            }
        } finally {
            IOUtils.closeQuietly(ldifReader);
        }
    }

    private void checkPartition(LdifEntry ldifEntry) throws Exception {
        Dn dn = ldifEntry.getDn();
        Dn parent = dn.getParent();
        try {
            directoryService.getAdminSession().exists(parent);
        } catch (Exception e) {
            System.out.println("Creating new partition for DN=" + dn + "\n");
            AvlPartition partition = new AvlPartition(directoryService.getSchemaManager());
            partition.setId(dn.getName());
            partition.setSuffixDn(dn);
            directoryService.addPartition(partition);
        }
    }

    private String formatPossibleIpv6(String host) {
        return (host != null && host.contains(":")) ? "[" + host + "]" : host;
    }

}
