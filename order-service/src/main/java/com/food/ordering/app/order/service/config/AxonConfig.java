package com.food.ordering.app.order.service.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.ExponentialBackOffIntervalRetryScheduler;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  @PersistenceContext
  private EntityManager entityManager;


  @Autowired
  public void configureProcessor(Configurer configurer) {
    configurer.eventProcessing().registerTrackingEventProcessor("test",
        org.axonframework.config.Configuration::eventStore,
        c -> c.getComponent(TrackingEventProcessorConfiguration.class,
            () -> TrackingEventProcessorConfiguration.forParallelProcessing(3)
                .andInitialSegmentsCount(1)
                .andInitialTrackingToken(StreamableMessageSource::createHeadToken)));
  }

  @Bean
  public CommandGateway commandGatewayWithRetry(Configurer configurer){
    CommandBus commandBus = configurer.buildConfiguration().commandBus();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    RetryScheduler rs = ExponentialBackOffIntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService).backoffFactor(1000).maxRetryCount(1000).build();
    CommandGateway commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).retryScheduler(rs).build();
    return commandGateway;
  }

  @Bean
  public SagaStore sagaStore() {
    return JpaSagaStore.builder()
        .entityManagerProvider(new SimpleEntityManagerProvider(entityManager))
        .serializer(JacksonSerializer.defaultSerializer())
        .build();
  }

//  @Autowired
//  public void configure(EventProcessingConfigurer configurer) {
//    configurer.registerPooledStreamingEventProcessor
//        ("ProcessOrderSagaProcessor",
//            org.axonframework.config.Configuration::eventStore,
//            (configuration, builder) -> builder.initialToken(
//                StreamableMessageSource::createTailToken));
//  }

//  @Bean
//  @Qualifier("defaultAxonXStream")
//  public XStream xStream() {
//    XStream xStream = new XStream();
//
//    xStream.allowTypesByWildcard(new String[]{
//        "com.food.ordering.app.**"
//    });
//    return xStream;
//  }

//  @Bean
//  public SagaStore mySagaStore(DataSource dataSource) {
//    return JdbcSagaStore.builder()
//        .dataSource(dataSource)
//        .build();
//  }

}
