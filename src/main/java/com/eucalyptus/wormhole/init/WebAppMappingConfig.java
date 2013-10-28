package com.eucalyptus.wormhole.init;

//@Configuration
//public class WebAppMappingConfig extends WebMvcConfigurationSupport {
//
//  final Logger logger = LoggerFactory.getLogger(WebAppMappingConfig.class);
//
//  @Bean
//  @Override
//  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//    logger.debug("BEGIN: Bean");
//
//    RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
//
//    Map<String, MediaType> fileExtensions = new TreeMap<>();
//    fileExtensions.put("css", MediaType.parseMediaType("text/css"));
//    fileExtensions.put("gif", MediaType.IMAGE_GIF);
//    fileExtensions.put("svg", MediaType.parseMediaType("image/svg+xml"));
//    fileExtensions.put("png", MediaType.IMAGE_PNG);
//    fileExtensions.put("jpg", MediaType.IMAGE_JPEG);
//    fileExtensions.put("jpeg", MediaType.IMAGE_JPEG);
//    PathExtensionContentNegotiationStrategy strategy = new PathExtensionContentNegotiationStrategy(fileExtensions);
//    ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager(strategy);
//    handlerMapping.setContentNegotiationManager(contentNegotiationManager);
//
//    handlerMapping.setUseRegisteredSuffixPatternMatch(true);
//
//    logger.debug("END: BEAN");
//    return handlerMapping;
//  }
//}