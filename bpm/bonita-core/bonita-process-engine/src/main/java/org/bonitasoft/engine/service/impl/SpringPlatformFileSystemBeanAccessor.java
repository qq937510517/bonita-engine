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
package org.bonitasoft.engine.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bonitasoft.platform.configuration.model.BonitaConfiguration;
import org.springframework.context.ApplicationContext;

/**
 * @author Matthieu Chaffotte
 * @author Charles Souillard
 * @author Celine Souchet
 */
public class SpringPlatformFileSystemBeanAccessor extends SpringFileSystemBeanAccessor {


    public SpringPlatformFileSystemBeanAccessor(ApplicationContext parent) {
        super(parent);
    }

    @Override
    protected Properties getProperties() throws IOException {
        return BONITA_HOME_SERVER.getPlatformProperties();
    }

    @Override
    protected List<BonitaConfiguration> getConfiguration() throws IOException {
        return BONITA_HOME_SERVER.getPlatformConfiguration();
    }

    @Override
    protected List<String> getClassPathResources(Properties properties) {
        ArrayList<String> resources = new ArrayList<>();
        resources.add("bonita-platform-community.xml");
        resources.add("bonita-platform-sp.xml");
        if (Boolean.valueOf(properties.getProperty("bonita.cluster", "false"))) {
            resources.add("bonita-platform-sp-cluster.xml");
        }
        return resources;
    }
}
