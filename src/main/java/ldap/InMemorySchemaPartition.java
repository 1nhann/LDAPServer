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
    }

}
