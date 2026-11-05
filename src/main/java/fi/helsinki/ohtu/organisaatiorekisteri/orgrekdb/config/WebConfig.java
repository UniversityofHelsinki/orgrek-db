package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        JsonMapper jsonMapper = JsonMapper.builder()
                .findAndAddModules()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .build();
        builder.disableDefaults().withJsonConverter(new JacksonJsonHttpMessageConverter(jsonMapper));
    }
}
