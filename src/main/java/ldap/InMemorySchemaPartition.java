package ldap;

import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.server.core.partition.ldif.AbstractLdifPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;


public class InMemorySchemaPartition extends AbstractLdifPartition {

    private static Logger LOG = LoggerFactory.getLogger(InMemorySchemaPartition.class);


    public InMemorySchemaPartition(SchemaManager schemaManager) {
        super(schemaManager);
    }

    @Override
    protected void doInit() throws InvalidNameException, Exception {
        if (initialized)
            return;

        LOG.debug("Initializing schema partition " + getId());
        suffixDn.apply(schemaManager);
        super.doInit();

        // load schema
//        final Map<String, Boolean> resMap = ResourceMap.getResources(Pattern.compile("schema[/\\Q\\\\E]ou=schema.*"));
//        for (String resourcePath : new TreeSet<String>(resMap.keySet())) {
//            if (resourcePath.endsWith(".ldif")) {
//                URL resource = DefaultSchemaLdifExtractor.getUniqueResource(resourcePath, "Schema LDIF file");
//                LdifReader reader = new LdifReader(resource.openStream());
//                LdifEntry ldifEntry = reader.next();
//                reader.close();
//
//                Entry entry = new DefaultEntry(schemaManager, ldifEntry.getEntry());
//                // add mandatory attributes
//                if (entry.get(SchemaConstants.ENTRY_CSN_AT) == null) {
//                    entry.add(SchemaConstants.ENTRY_CSN_AT, defaultCSNFactory.newInstance().toString());
//                }
//                if (entry.get(SchemaConstants.ENTRY_UUID_AT) == null) {
//                    entry.add(SchemaConstants.ENTRY_UUID_AT, UUID.randomUUID().toString());
//                }
//                AddOperationContext addContext = new AddOperationContext(null, entry);
//                super.add(addContext);
//            }
//        }
    }

}
