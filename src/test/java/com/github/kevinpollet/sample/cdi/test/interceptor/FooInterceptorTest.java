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
package com.github.kevinpollet.sample.cdi.test.interceptor;

import com.github.kevinpollet.sample.cdi.interceptor.Foo;
import com.github.kevinpollet.sample.cdi.test.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

/**
 * @author Kevin Pollet
 */
@RunWith(Arquillian.class)
public class FooInterceptorTest {

   @Deployment
   public static Archive<?> deployment() {
      return Deployments.baseDeployment()
            .addPackage(FooInterceptorTest.class.getPackage())
            .addAsWebInfResource(FooInterceptorTest.class.getResource("beans.xml"), "beans.xml");
   }

   @Inject
   public GreetingService greetingService;

   @Inject
   public BeanManager beanManager;

   @Test
   public void testFooInterceptor() {
      String hi = greetingService.hi("Kevin");
      String bye = greetingService.bye("Kevin");

      Assert.assertEquals(1, beanManager.resolveInterceptors(InterceptionType.AROUND_INVOKE, new AnnotationLiteral<Foo>() {}).size());
      Assert.assertEquals("Foo interceptor called", hi);
      Assert.assertEquals("Bye Kevin", bye);
   }
}