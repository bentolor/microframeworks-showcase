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

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jodd.madvoc.MadvocContextListener;
import jodd.madvoc.MadvocServletFilter;
import jodd.servlet.RequestContextListener;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;

/**
 * Central class to bootstrap the grocery lists demo application via an inline Undertow server.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class Main {

    public static void main(final String[] args) throws ServletException {
        ClassLoader classLoader = AppWebApplication.class.getClassLoader();
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(classLoader)
                .setContextPath("/")
                .setResourceManager(new ClassPathResourceManager(classLoader, "webapp"))
                .setDeploymentName("grocerylists-jodd.war")
                .addListener(Servlets.listener(MadvocContextListener.class))
                .addServletContextAttribute("madvoc.webapp", "bentolor.grocerylist.AppWebApplication")
                .addServletContextAttribute("madvoc.params", "/madvoc.properties")
                .addListener(Servlets.listener(RequestContextListener.class))
                .addFilter(Servlets.filter("madvoc", MadvocServletFilter.class))
                .addFilterUrlMapping("madvoc", "/*", DispatcherType.REQUEST);

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(Handlers.path(manager.start()))
                .build();
        server.start();
    }
}
