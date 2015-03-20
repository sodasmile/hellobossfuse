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
import org.junit.Test;

public class HelloRouteBuilderTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Produce(uri = "direct:timerTriggered")
    protected ProducerTemplate directTimerTrigged;

    @PropertyInject("property1")
    protected Properties properties;

    @Test
    public void testSendMatchingMessage() throws Exception {
        String expectedBody = "<matched/>";

        resultEndpoint.expectedBodiesReceived(expectedBody);

        template.sendBodyAndHeader(expectedBody, "foo", "bar");

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendNotMatchingMessage() throws Exception {
        resultEndpoint.expectedMessageCount(0);

        template.sendBodyAndHeader("<notMatched/>", "foo", "notMatchedHeaderValue");

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testName() throws Exception {
        directTimerTrigged.sendBody(null);
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties properties = new Properties();
        properties.setProperty("property1", "provla");
        return properties;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .filter(header("foo")
                        .isEqualTo("bar"))
                        .to("mock:result");

                from("timer:routeBuilderTimer?period=3s")
                        .to("direct:timerTriggered");

                from("direct:timerTriggered")
                        .routeId("recurrentLoggingRoute")
                        .log(LoggingLevel.INFO, "en melding fra route builder ");
            }
        };
    }
}