package org.drools.dsl.firealarm.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Alarm;
import org.drools.dsl.firealarm.model.Fire;

public class RaiseTheAlarmWhenWeHaveOneOrMoreFires extends AbstractJavaRule {

    @Override
    public boolean lhs() {
        return exists(Fire.class);
    }

    @Override
    public void rhs() {
        drools.insert(new Alarm());
        System.out.println( "Raise the alarm" );
    }
}