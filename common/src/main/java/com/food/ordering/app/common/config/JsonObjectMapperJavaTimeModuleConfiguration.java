package com.food.ordering.app.common.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({JSonMapper.class, JavaTimeModule.class})
public class JsonObjectMapperJavaTimeModuleConfiguration {


  public JsonObjectMapperJavaTimeModuleConfiguration() {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
  }
}
