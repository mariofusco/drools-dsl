package org.drools.dsl.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.model.Message;

public class HiUniverseRule extends AbstractJavaRule {

    private Message $msg;

    public boolean lhs() {
        return $msg.getMessage().equals("Hi Universe");
    }

    public void rhs() {
        System.out.println($msg.getMessage());
    }
}