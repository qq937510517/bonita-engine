/**
 * Copyright (C) 2019 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
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
package org.bonitasoft.engine.core.process.definition.model.builder.event.trigger.impl;

import org.bonitasoft.engine.core.process.definition.model.builder.event.trigger.SThrowErrorEventTriggerDefinitionBuilder;
import org.bonitasoft.engine.core.process.definition.model.builder.event.trigger.SThrowErrorEventTriggerDefinitionBuilderFactory;
import org.bonitasoft.engine.core.process.definition.model.event.trigger.SThrowErrorEventTriggerDefinition;
import org.bonitasoft.engine.core.process.definition.model.event.trigger.impl.SThrowErrorEventTriggerDefinitionImpl;

/**
 * @author Baptiste Mesta
 */
public class SThrowErrorEventTriggerDefinitionBuilderFactoryImpl
        implements SThrowErrorEventTriggerDefinitionBuilderFactory {

    @Override
    public SThrowErrorEventTriggerDefinitionBuilder createNewInstance(final String errorCode) {
        final SThrowErrorEventTriggerDefinition entity = new SThrowErrorEventTriggerDefinitionImpl(errorCode);
        return new SThrowErrorEventTriggerDefinitionBuilderImpl(entity);
    }

}
