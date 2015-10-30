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
package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import persistence.DefaultModelSerializer;
import persistence.ModelSerializer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Singleton
public class Module extends AbstractModule {

    protected void configure() {
        bind(ModelSerializer.class).to(DefaultModelSerializer.class);
        bind(File.class).annotatedWith(Names.named("datastore")).toInstance(new File("grocerylists.json"));
        bind(DateFormat.class).annotatedWith(Names.named("json-dateformat")).toProvider(JsonDateFormatProvider.class);
    }

    public static class JsonDateFormatProvider implements Provider<DateFormat> {
        @Override
        public DateFormat get() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    }


}
