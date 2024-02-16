//package com.food.ordering.app.payment.service.aggregate;
//
//import java.util.List;
//import org.axonframework.config.Configurer;
//import org.axonframework.config.EventProcessingConfigurer;
//import org.axonframework.eventhandling.EventMessage;
//import org.axonframework.extensions.kafka.KafkaProperties;
//import org.axonframework.extensions.kafka.configuration.KafkaMessageSourceConfigurer;
//import org.axonframework.extensions.kafka.eventhandling.DefaultKafkaMessageConverter;
//import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
//import org.axonframework.extensions.kafka.eventhandling.consumer.AsyncFetcher;
//import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
//import org.axonframework.extensions.kafka.eventhandling.consumer.DefaultConsumerFactory;
//import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
//import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
//import org.axonframework.extensions.kafka.eventhandling.producer.DefaultProducerFactory;
//import org.axonframework.extensions.kafka.eventhandling.producer.KafkaEventPublisher;
//import org.axonframework.extensions.kafka.eventhandling.producer.KafkaPublisher;
//import org.axonframework.extensions.kafka.eventhandling.producer.ProducerFactory;
//import org.axonframework.serialization.json.JacksonSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KafkaConfig {
//
//
//  @Bean
//  public ProducerFactory<String, byte[]> producerFactory(KafkaProperties producerConfiguration) {
//    return DefaultProducerFactory.<String, byte[]>builder()
//        .configuration(producerConfiguration.buildProducerProperties()) // Hard requirement
//        .build();
//  }
//
//  @Bean
//  public KafkaPublisher<String, byte[]> kafkaPublisher(ProducerFactory<String, byte[]> producerFactory,
//      KafkaMessageConverter<String, byte[]> kafkaMessageConverter) {
//    KafkaPublisher<String, byte[]> kafkaPublisher = KafkaPublisher.<String, byte[]>builder()
//        .topic("local.event")                          // Defaults to "Axon.Events"
//        .producerFactory(producerFactory)           // Hard requirement
//        .messageConverter(kafkaMessageConverter)    // Defaults to a "DefaultKafkaMessageConverter"
//        .build();
//    return kafkaPublisher;
//  }
//
//  @Bean
//  public KafkaEventPublisher<String, byte[]> kafkaEventPublisher(KafkaPublisher<String, byte[]> kafkaPublisher) {
//    KafkaEventPublisher<String, byte[]> kafkaEventPublisher = KafkaEventPublisher.<String, byte[]>builder()
//        .kafkaPublisher(kafkaPublisher)             // Hard requirement
//        .build();
//
//    return kafkaEventPublisher;
//  }
//
//  @Autowired
//  public void registerPublisherToEventProcessor(EventProcessingConfigurer eventProcessingConfigurer,
//      KafkaEventPublisher<String, byte[]> kafkaEventPublisher) {
//    String processingGroup = KafkaEventPublisher.DEFAULT_PROCESSING_GROUP;
//    eventProcessingConfigurer.registerEventHandler(configuration -> kafkaEventPublisher)
//        .assignHandlerTypesMatching(
//            processingGroup,
//            clazz -> clazz.isAssignableFrom(KafkaEventPublisher.class)
//        )
//        .registerSubscribingEventProcessor(processingGroup);
//    // Replace `registerSubscribingEventProcessor` for `registerTrackingEventProcessor` to use a tracking processor
//  }
//
//  @Bean
//  public ConsumerFactory<String, byte[]> consumerFactory(KafkaProperties consumerConfiguration) {
//    return new DefaultConsumerFactory<>(consumerConfiguration.buildConsumerProperties());
//  }
//
//  @Bean
//  public Fetcher<?, ?, ?> fetcher() {
//    return AsyncFetcher.builder()
//        .build();
//  }
//
//  @Bean
//  public KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer(Configurer configurer) {
//    KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer = new KafkaMessageSourceConfigurer();
//    configurer.registerModule(kafkaMessageSourceConfigurer);
//    return kafkaMessageSourceConfigurer;
//  }
//
//  @Bean
//  public SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource(ConsumerFactory<String, byte[]> consumerFactory,
//      Fetcher<String, byte[], EventMessage<?>> fetcher,
//      KafkaMessageConverter<String, byte[]> messageConverter,
//      KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer) {
//    SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource = SubscribableKafkaMessageSource.<String, byte[]>builder()
//        .topics(List.of("local.event"))
//        .groupId("kebab")                   // Hard requirement
//        .consumerFactory(consumerFactory)   // Hard requirement
//        .fetcher(fetcher)                   // Hard requirement
//        .messageConverter(messageConverter) // Defaults to a "DefaultKafkaMessageConverter"
//        .build();
//    // Registering the source is required to tie into the Configurers lifecycle to start the source at the right stage
//    kafkaMessageSourceConfigurer.configureSubscribableSource(configuration -> subscribableKafkaMessageSource);
//    return subscribableKafkaMessageSource;
//  }
//
//  @Autowired
//  public void configureSubscribableKafkaSource(EventProcessingConfigurer eventProcessingConfigurer,
//      SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource) {
//    eventProcessingConfigurer.assignProcessingGroup("kebab", "idk");
//    eventProcessingConfigurer.registerSubscribingEventProcessor(
//        "idk",
//        configuration -> subscribableKafkaMessageSource
//    );
//  }
//
//  @Bean
//  KafkaMessageConverter<String, byte[]> kafkaMessageConverter() {
//    JacksonSerializer serializer = JacksonSerializer.defaultSerializer();
//    return DefaultKafkaMessageConverter.builder()
//        .serializer(serializer)
//        .build();
//  }
//}
