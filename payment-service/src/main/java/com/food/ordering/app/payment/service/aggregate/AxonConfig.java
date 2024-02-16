package com.food.ordering.app.payment.service.aggregate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  @PersistenceContext
  private EntityManager entityManager;

//  @Autowired
//  public void configureProcessor(Configurer configurer) {
//    configurer.eventProcessing().registerTrackingEventProcessor("test",
//        org.axonframework.config.Configuration::eventStore,
//        c -> c.getComponent(TrackingEventProcessorConfiguration.class,
//            () -> TrackingEventProcessorConfiguration.forParallelProcessing(3)
//                .andInitialSegmentsCount(1)
//                .andInitialTrackingToken(StreamableMessageSource::createHeadToken)));
//  }

//  @Bean
//  public KafkaConsumer<String, byte[]> kafkaConsumer() {
//    Properties properties = new Properties();
//    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, List.of("http://localhost:19092", "http://localhost:29092", "http://localhost:39092"));
//    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-servicos");
//    // Add more Kafka consumer properties as needed
//
//    // Key and value deserializers
//    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
//
//    return new KafkaConsumer<>(properties);
//  }


//  @Autowired
//  public void registerProcessor(
//      EventProcessingConfigurer configurer,
//      StreamableKafkaMessageSource<String, byte[]> streamableKafkaMessageSource){
//    configurer.registerPooledStreamingEventProcessor("kafka-group", c -> streamableKafkaMessageSource);
//  }

//  @Bean
//  public KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer() {
//    return new KafkaMessageSourceConfigurer();
//  }

//  @Bean
//  public SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource(
//      org.axonframework.extensions.kafka.KafkaProperties kafkaProperties,
//      ConsumerFactory<String, byte[]> consumerFactory,
//      Fetcher<String, byte[], EventMessage<?>> fetcher,
//      KafkaMessageConverter<String, byte[]> messageConverter,
//      KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer) {
//    SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource = SubscribableKafkaMessageSource.<String, byte[]>builder()
//        .topics(List.of("local.event"))
//        .groupId("kebab")
//        .consumerFactory(consumerFactory)
//        .fetcher(fetcher)
//        .autoStart()
//        .serializer(JacksonSerializer.defaultSerializer())
//        .messageConverter(messageConverter)
//        .build();
//    // Registering the source is required to tie into the Configurers lifecycle to start the source at the right stage
//    kafkaMessageSourceConfigurer.configureSubscribableSource(configuration -> subscribableKafkaMessageSource);
//    return subscribableKafkaMessageSource;
//  }


//  @Autowired
//  public void registerKafkaMessageSourceConfigurer(
//      Configurer configurer, KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer) {
//    configurer.registerModule(kafkaMessageSourceConfigurer);
//  }

//  @Autowired
//  public void configureSubscribableKafkaSource(
//      KafkaProperties kafkaProperties,
//      EventProcessingConfigurer eventProcessingConfigurer,
//      SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource) {
//    eventProcessingConfigurer.registerSubscribingEventProcessor(
//        kafkaProperties.getConsumer().getProperties().get("processor-name"),
//        configuration -> subscribableKafkaMessageSource);
//  }


//  @Bean
//  public SagaStore sagaStore() {
//    return JpaSagaStore.builder()
//        .entityManagerProvider(new SimpleEntityManagerProvider(entityManager))
//        .serializer(JacksonSerializer.defaultSerializer())
//        .build();
//  }


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
