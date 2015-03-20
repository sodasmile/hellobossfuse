package com.sodasmile.jbossfuse.hellofuse;

import java.util.Map;
import java.util.Properties;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;

public class HelloRouteBuilderJunitTest extends CamelTestSupport {

    @Produce(uri = "direct:timerTriggered")
    protected ProducerTemplate directTimerTrigged;

    @Test
    @Ignore("Is it really impossible to get the thing to mock the property in the route??")
    public void testName() throws Exception {
        directTimerTrigged.sendBody(null);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = super.createCamelContext();
        camelContext.resolvePropertyPlaceholders("nisse");
        Map<String, String> properties = camelContext.getProperties();
        properties.put("proper", "doper");
        camelContext.setProperties(properties);
        return camelContext;
    }

    @Override
    protected Boolean ignoreMissingLocationWithPropertiesComponent() {
        return true;
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties properties = new Properties();
        properties.setProperty("prop", "provla");
        return properties;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new HelloRouteBuilder();
    }
}