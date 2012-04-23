package org.bonitasoft.engine.test;

import java.util.List;

import org.bonitasoft.engine.events.EventService;
import org.bonitasoft.engine.identity.IdentityService;
import org.bonitasoft.engine.identity.model.builder.IdentityModelBuilder;
import org.bonitasoft.engine.log.technical.TechnicalLoggerService;
import org.bonitasoft.engine.platform.PlatformService;
import org.bonitasoft.engine.platform.model.builder.SPlatformBuilder;
import org.bonitasoft.engine.platform.model.builder.STenantBuilder;
import org.bonitasoft.engine.recorder.Recorder;
import org.bonitasoft.engine.services.PersistenceService;
import org.bonitasoft.engine.session.SessionService;
import org.bonitasoft.engine.sessionaccessor.SessionAccessor;
import org.bonitasoft.engine.test.util.BaseServicesBuilder;
import org.bonitasoft.engine.transaction.TransactionService;

/**
 * You can set these system properties to change implementations
 * bonita.test.db.vendor
 * change the DB:
 * h2 (default)
 * mysql
 * oracle
 * postgres
 * bonita.test.identity.service
 * change the identity service
 * bonita-identity-impl (default)
 * bonita.test.identity.model
 * change the identity model
 * bonita-identity-model-impl (default)
 * 
 * @author Elias Ricken de Medeiros
 */
public class ServicesBuilder extends BaseServicesBuilder {

    public static final String BONITA_EVENT_IMPL_SP = "bos-events-api-impl";

    @Override
    protected List<String> getResourceList() {
        System.setProperty(BaseServicesBuilder.BONITA_TEST_EVENT_SERVICE, BONITA_EVENT_IMPL_SP);
        final List<String> resourceList = super.getResourceList();
        return resourceList;
    }

    public IdentityModelBuilder buildIdentityModelBuilder() {
        return this.getInstanceOf(IdentityModelBuilder.class);
    }

    public PersistenceService buildPersistence() {
        return this.buildPersistence("persistenceService");
    }

    public PersistenceService buildPersistence(final String name) {
        return this.getInstanceOf(name, PersistenceService.class);
    }

    public PersistenceService buildJournal() {
        return this.buildPersistence();
    }

    public PersistenceService buildHistory() {
        return this.buildPersistence("history");
    }

    public Recorder buildRecorder(final boolean sync) {
        String synchType = "recorderAsync";
        if (sync) {
            synchType = "recorderSync";
        }
        return this.getInstanceOf(synchType, Recorder.class);
    }

    public TransactionService buildTransactionService() {
        return this.getInstanceOf(TransactionService.class);
    }

    public PlatformService buildPlatformService() {
        return this.getInstanceOf(PlatformService.class);
    }

    public SPlatformBuilder buildPlatformBuilder() {
        return this.getInstanceOf(SPlatformBuilder.class);
    }

    public STenantBuilder buildTenantBuilder() {
        return this.getInstanceOf(STenantBuilder.class);
    }

    public SessionAccessor buildSessionAccessor() {
        return this.getInstanceOf(SessionAccessor.class);
    }

    public IdentityService buildIdentityService() {
        return this.getInstanceOf(IdentityService.class);
    }

    public EventService buildEventService() {
        return this.getInstanceOf(EventService.class);
    }

    public SessionService buildSessionService() {
        return this.getInstanceOf(SessionService.class);
    }

    public TechnicalLoggerService buildTechnicalLoggerService() {
        return this.getInstanceOf(TechnicalLoggerService.class);
    }

}
