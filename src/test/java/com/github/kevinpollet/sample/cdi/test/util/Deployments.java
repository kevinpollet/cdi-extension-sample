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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Kevin Pollet
 */
public class Deployments {

   // ARQ-403, Open Web Beans embedded container doesn't support WebArchive
   public static JavaArchive baseDeployment() {
      return ShrinkWrap.create(JavaArchive.class)
            .addPackage(SampleExtension.class.getPackage())
            .addPackage(Foo.class.getPackage())
            .addAsManifestResource(Foo.class.getResource("/META-INF/services/javax.enterprise.inject.spi.Extension"), "/services/javax.enterprise.inject.spi.Extension")
            .addPackages(true, "org.jboss.seam.solder");
   }
}
