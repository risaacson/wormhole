package com.eucalyptus.wormhole.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.TreeMap;

@Configuration
public class WebAppMappingConfig extends WebMvcConfigurationSupport {

  final Logger logger = LoggerFactory.getLogger(WebAppMappingConfig.class);

  @Bean
  @Override
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    logger.debug("ENTER: Bean");

    RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
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
    handlerMapping.setUseRegisteredSuffixPatternMatch(true);
    return handlerMapping;
  }
}
