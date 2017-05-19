/**
 * Copyright (C) 2015 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package org.bonitasoft.engine.recorder.impl;

import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.events.EventService;
import org.bonitasoft.engine.events.model.SDeleteEvent;
import org.bonitasoft.engine.events.model.SEvent;
import org.bonitasoft.engine.events.model.SFireEventException;
import org.bonitasoft.engine.events.model.SInsertEvent;
import org.bonitasoft.engine.events.model.SUpdateEvent;
import org.bonitasoft.engine.log.technical.TechnicalLogSeverity;
import org.bonitasoft.engine.log.technical.TechnicalLoggerService;
import org.bonitasoft.engine.persistence.PersistentObject;
import org.bonitasoft.engine.recorder.Recorder;
import org.bonitasoft.engine.recorder.SRecorderException;
import org.bonitasoft.engine.recorder.model.DeleteAllRecord;
import org.bonitasoft.engine.recorder.model.DeleteRecord;
import org.bonitasoft.engine.recorder.model.InsertRecord;
import org.bonitasoft.engine.recorder.model.UpdateRecord;
import org.bonitasoft.engine.services.PersistenceService;
import org.bonitasoft.engine.services.UpdateDescriptor;

/**
 * @author Charles Souillard
 * @author Baptiste Mesta
 * @author Celine Souchet
 */
public class RecorderImpl implements Recorder {

    private final TechnicalLoggerService logger;

    private final PersistenceService persistenceService;

    private final EventService eventService;

    public RecorderImpl(final PersistenceService persistenceService, final TechnicalLoggerService logger,
            final EventService eventService) {
        this.persistenceService = persistenceService;
        this.logger = logger;
        this.eventService = eventService;
    }

    @Override
    public void recordInsert(final InsertRecord record, final SInsertEvent insertEvent) throws SRecorderException {
        try {
            persistenceService.insert(record.getEntity());
            if (insertEvent != null) {
                eventService.fireEvent(createInsertEvent(record.getEntity(), insertEvent.getType()));
            }
        } catch (final Exception e) {
            logExceptionsFromHandlers(e);
            throw new SRecorderException(e);
        }
    }

    private SInsertEvent createInsertEvent(PersistentObject entity, String type) {
        SInsertEvent sInsertEvent = new SInsertEvent(type + SEvent.CREATED);
        sInsertEvent.setObject(entity);
        return sInsertEvent;
    }

    private SDeleteEvent createDeleteEvent(PersistentObject entity, String type) {
        SDeleteEvent sDeleteEvent = new SDeleteEvent(type + SEvent.DELETED);
        sDeleteEvent.setObject(entity);
        return sDeleteEvent;
    }
    private SUpdateEvent createUpdateEvent(PersistentObject entity, Map<String, Object> updatedFields, String type) {
        SUpdateEvent sUpdateEvent = new SUpdateEvent(type + SEvent.UPDATED);
        sUpdateEvent.setObject(entity);
        sUpdateEvent.setUpdatedFields(updatedFields);
        return sUpdateEvent;
    }

    @Override
    public void recordDelete(final DeleteRecord record, final SDeleteEvent deleteEvent) throws SRecorderException {
        try {
            persistenceService.delete(record.getEntity());
            if (deleteEvent != null) {
                eventService.fireEvent(createDeleteEvent(record.getEntity(), deleteEvent.getType()));
            }
        } catch (final Exception e) {
            logExceptionsFromHandlers(e);
            throw new SRecorderException(e);
        }
    }

    @Override
    public void recordDeleteAll(final DeleteAllRecord record) throws SRecorderException {
        try {
            persistenceService.deleteByTenant(record.getEntityClass(), record.getFilters());
        } catch (final Exception e) {
            logExceptionsFromHandlers(e);
            throw new SRecorderException(e);
        }
    }

    @Override
    public void recordUpdate(final UpdateRecord record, final SUpdateEvent updateEvent) throws SRecorderException {
        final UpdateDescriptor desc = UpdateDescriptor.buildSetFields(record.getEntity(), record.getFields());
        try {
            persistenceService.update(desc);
            if (updateEvent != null) {
                eventService.fireEvent(createUpdateEvent(record.getEntity(), record.getFields(), updateEvent.getType()));
            }
        } catch (final Exception e) {
            logExceptionsFromHandlers(e);
            throw new SRecorderException(e);
        }
    }

    private void logExceptionsFromHandlers(final Exception e)
            throws SRecorderException {
        if (!(e instanceof SFireEventException)) {
            return;
        }
        final List<Exception> handlerExceptions = ((SFireEventException) e).getHandlerExceptions();
        for (Exception exception : handlerExceptions) {
            logger.log(this.getClass(), TechnicalLogSeverity.TRACE, "error while executing handler", exception);
        }
    }

}
