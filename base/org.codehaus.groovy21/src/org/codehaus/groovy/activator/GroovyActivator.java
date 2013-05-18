 /*
 * Copyright 2003-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.activator;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class GroovyActivator extends Plugin {

    public static final String PLUGIN_ID = "org.codehaus.groovy"; //$NON-NLS-1$
    
	public static final String GROOVY_ALL_JAR = "lib/groovy-all-2.1.3.jar"; //$NON-NLS-1$

    public static URL GROOVY_ALL_JAR_URL;
    
    private static GroovyActivator DEFAULT;

    public GroovyActivator() {
        DEFAULT = this;
    }
    
    public static GroovyActivator getDefault() {
        return DEFAULT;
    }
    
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        try {
            initialize();
        } catch (Exception e) {
            getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "Error starting groovy plugin", e));
        }
    }

    public static void initialize() throws IOException {
        Bundle bundle = Platform.getBundle("org.codehaus.groovy");
        GROOVY_ALL_JAR_URL = FileLocator.resolve(bundle.getEntry(GroovyActivator.GROOVY_ALL_JAR));
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
    }
}