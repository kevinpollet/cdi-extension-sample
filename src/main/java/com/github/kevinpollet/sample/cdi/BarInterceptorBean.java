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
import com.github.kevinpollet.sample.cdi.interceptor.BarInterceptor;
import org.jboss.seam.solder.bean.BeanBuilder;
import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.util.AnnotationLiteral;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kevin Pollet
 */
public class BarInterceptorBean implements Interceptor<BarInterceptor> {

   private Bean<BarInterceptor> bean;
   private Set<Annotation> interceptorBindings;

   public BarInterceptorBean(BeanManager beanManager) {
      this.interceptorBindings = new HashSet<Annotation>();
      this.interceptorBindings.add(new AnnotationLiteral<Bar>() {});

      AnnotatedType<BarInterceptor> annotatedType = new AnnotatedTypeBuilder<BarInterceptor>()
            .readFromType(BarInterceptor.class)
            .addToClass(new AnnotationLiteral<Dependent>() {})
            .addToClass(new AnnotationLiteral<Bar>() {})
            .create();

      // creates the bean
      this.bean = new BeanBuilder<BarInterceptor>(beanManager)
            .readFromType(annotatedType)
            .create();
   }

   public Set<Annotation> getInterceptorBindings() {
      return interceptorBindings;
   }

   public boolean intercepts(InterceptionType type) {
      return type.equals(InterceptionType.AROUND_INVOKE);
   }

   public Object intercept(InterceptionType type, BarInterceptor instance, InvocationContext ctx) {
      try {

         if (intercepts(type)) {
            return instance.intercept(ctx);
         }
         return ctx.proceed();

      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   public Set<Type> getTypes() {
      return bean.getTypes();
   }

   public Set<Annotation> getQualifiers() {
      return bean.getQualifiers();
   }

   public Class<? extends Annotation> getScope() {
      return bean.getScope();
   }

   public String getName() {
      return bean.getName();
   }

   public Set<Class<? extends Annotation>> getStereotypes() {
      return bean.getStereotypes();
   }

   public Class<?> getBeanClass() {
      return bean.getBeanClass();
   }

   public boolean isAlternative() {
      return bean.isAlternative();
   }

   public boolean isNullable() {
      return bean.isNullable();
   }

   public Set<InjectionPoint> getInjectionPoints() {
      return bean.getInjectionPoints();
   }

   public BarInterceptor create(CreationalContext<BarInterceptor> creationalContext) {
      return bean.create(creationalContext);
   }

   public void destroy(BarInterceptor instance, CreationalContext<BarInterceptor> creationalContext) {
      bean.destroy(instance, creationalContext);
   }
}
