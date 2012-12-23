package org.drools.dsl.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.model.Message;

public class HelloWorldRule extends AbstractJavaRule {

    private Message $msg;

    public boolean lhs() {
        // return $msg.getMessage().equals("Hello World");
        return $msg.getMessage().indexOf('W') > 3;
    }

    public void rhs() {
        drools.insert(new Message("Hi Universe"));
    }
}
