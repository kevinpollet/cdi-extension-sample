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

import com.github.kevinpollet.sample.cdi.interceptor.Bar;
import com.github.kevinpollet.sample.cdi.interceptor.Foo;

/**
 * @author Kevin Pollet
 */
public class GreetingService {

   @Foo
   public String hi(String user) {
      return "Hi " + user;
   }

   @Foo(false)
   public String hey(String user) {
      return "Hey " + user;
   }

   @Bar
   public String hello(String user) {
      return "Hello " + user;
   }

   public String bye(String user) {
      return "Bye " + user;
   }
}
