package org.drools.dsl.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.model.Message;

public class HelloWorldRule extends AbstractJavaRule {

    private Message $msg;

    public boolean lhs() {
        // return $msg.getMessage().equals("Hello World");
        String message = $msg.getMessage();
        return message.indexOf('W') > 3 && message.indexOf('U') < 0;
    }

    public void rhs() {
        drools.insert(new Message("Hi Universe"));
    }
}
