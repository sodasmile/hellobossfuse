package com.sodasmile.jbossfuse.hellofuse;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloRouteBuilder extends RouteBuilder {

    private Logger logger = LoggerFactory.getLogger(HelloRouteBuilder.class);

    public void configure() throws Exception {
        logger.info("Configuring HelloRoute!");
        from("timer:routeBuilderTimer?period=3s")
                .routeId("recurrentLoggingRoute")
                .log(LoggingLevel.INFO, "en melding fra route builder");
    }
}


