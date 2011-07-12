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
package com.github.kevinpollet.sample.cdi;

import com.github.kevinpollet.sample.cdi.interceptor.Bar;
import com.github.kevinpollet.sample.cdi.interceptor.Foo;
import com.github.kevinpollet.sample.cdi.interceptor.FooInterceptor;
import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import javax.interceptor.Interceptor;

/**
 * @author Kevin Pollet
 */
public class SampleExtension implements Extension {

   void registerInterceptorBinding(@Observes BeforeBeanDiscovery event) {
      event.addInterceptorBinding(Foo.class);
      event.addInterceptorBinding(Bar.class);
   }

   void processFooInterceptorAnnotatedType(@Observes ProcessAnnotatedType<FooInterceptor> event) {
      AnnotatedType<FooInterceptor> annotatedType = new AnnotatedTypeBuilder<FooInterceptor>()
            .readFromType(event.getAnnotatedType())
            .addToClass(new AnnotationLiteral<Interceptor>() {})
            .addToClass(new AnnotationLiteral<Foo>() {})
            .create();

      event.setAnnotatedType(annotatedType);
   }

   void registerBarInterceptor(@Observes AfterBeanDiscovery event, BeanManager beanManager) {
      event.addBean(new BarInterceptorBean(beanManager));
   }
}
