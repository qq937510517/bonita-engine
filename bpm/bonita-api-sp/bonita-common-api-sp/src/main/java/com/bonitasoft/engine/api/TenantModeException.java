/*******************************************************************************
 * Copyright (C) 2013 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 * BonitaSoft, 32 rue Gustave Eiffel – 38000 Grenoble
 * or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package com.bonitasoft.engine.api;

import org.bonitasoft.engine.exception.BonitaRuntimeException;

/**
 * @author Emmanuel Duchastenier
 */
public class TenantModeException extends BonitaRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            the exception message
     */
    public TenantModeException(final String message) {
        super(message);
    }

}