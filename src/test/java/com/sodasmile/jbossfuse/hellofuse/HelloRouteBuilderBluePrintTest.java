package com.sodasmile.jbossfuse.hellofuse;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class HelloRouteBuilderBluePrintTest  extends CamelBlueprintTestSupport {

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/camel-context.xml";
    }

    @Produce(uri = "direct:timerTriggered")
    protected ProducerTemplate directTimerTrigged;

    @Test
    public void testName() throws Exception {
        directTimerTrigged.sendBody(null);

    }
}
