/*
 *    Copyright 2015 Benjamin Schmid, @bentolor
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package bentolor.grocerylist;

import jodd.log.LoggerFactory;
import jodd.log.impl.SimpleLogger;
import jodd.madvoc.WebApp;
import jodd.madvoc.action.MadvocConfigurations;

public class AppWebApplication extends WebApp {

    public AppWebApplication() {
        super();
        LoggerFactory.setLoggerProvider(SimpleLogger.PROVIDER);
    }

    @Override
    protected void configureDefaults() {
        super.configureDefaults();
        // set the root package to be where the index action is
        madvocContainer.lookupComponent(RootPackages.class).addRootPackageOf(ListAction.class);
    }
}