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
