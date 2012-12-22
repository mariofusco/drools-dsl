package org.drools.dsl;

import org.drools.dsl.model.Message;
import org.drools.dsl.rules.HelloWorldRule;
import org.drools.dsl.rules.HiUniverseRule;
import org.junit.Test;
import org.kie.KnowledgeBase;
import org.kie.KnowledgeBaseFactory;
import org.kie.runtime.StatefulKnowledgeSession;

import static junit.framework.Assert.assertEquals;

public class DroolsDslTest {

    @Test
    public void testDsl() {
        DslPackageBuilder dslPackageBuilder = new DslPackageBuilder();
        dslPackageBuilder.addRule(HelloWorldRule.class);
        dslPackageBuilder.addRule(HiUniverseRule.class);

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( dslPackageBuilder.getKnowledgePackages() );

        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        ksession.insert( new Message( "Hello World" ) );
        assertEquals(2, ksession.fireAllRules());
    }
}
