package org.drools.dsl.firealarm.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Alarm;
import org.drools.dsl.firealarm.model.Fire;

public class CancelTheAlarmWhenAllTheFiresHaveGone extends AbstractJavaRule {

    private Alarm $alarm;

    @Override
    public boolean lhs() {
        return not(Fire.class);
    }

    @Override
    public void rhs() {
        drools.retract($alarm);
        System.out.println( "Cancel the alarm" );
    }
}