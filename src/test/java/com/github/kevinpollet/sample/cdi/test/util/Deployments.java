/*
 * Copyright 2011 Kevin Pollet
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
package com.github.kevinpollet.sample.cdi.test.util;

import com.github.kevinpollet.sample.cdi.SampleExtension;
import com.github.kevinpollet.sample.cdi.interceptor.Foo;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

/**
 * @author Kevin Pollet
 */
public class Deployments {

   public static WebArchive baseDeployment() {
      return ShrinkWrap.create(WebArchive.class)
            .addAsLibrary(
                  ShrinkWrap.create(JavaArchive.class)
                        .addPackage(SampleExtension.class.getPackage())
                        .addPackage(Foo.class.getPackage())
                        .addAsManifestResource(Foo.class.getResource("/META-INF/services/javax.enterprise.inject.spi.Extension"), "/services/javax.enterprise.inject.spi.Extension")
                        .addAsManifestResource(Foo.class.getResource("/META-INF/beans.xml"), "beans.xml")
            )
            .addAsLibraries(
                  DependencyResolvers.use(MavenDependencyResolver.class)
                        .loadReposFromPom("pom.xml")
                        .artifact("org.jboss.seam.solder:seam-solder")
                        .resolveAs(GenericArchive.class)
            );
   }
}
