package org.drools.dsl.firealarm.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Alarm;
import org.drools.dsl.firealarm.model.Sprinkler;

public class StatusOutputWhenThingsAreOk extends AbstractJavaRule {

    private Sprinkler $sprinkler;

    @Override
    public boolean lhs() {
        return not(Alarm.class) && not($sprinkler.isOn());
    }

    @Override
    public void rhs() {
        System.out.println( "Everything is ok" );
    }
}