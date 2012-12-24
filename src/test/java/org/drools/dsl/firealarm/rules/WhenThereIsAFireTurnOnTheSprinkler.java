package org.drools.dsl.firealarm.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Fire;
import org.drools.dsl.firealarm.model.Sprinkler;

public class WhenThereIsAFireTurnOnTheSprinkler extends AbstractJavaRule {

    private Fire $fire;
    private Sprinkler $sprinkler;

    @Override
    public boolean lhs() {
        return $sprinkler.getRoom().equals($fire.getRoom()) && !$sprinkler.isOn();
    }

    @Override
    public void rhs() {
        $sprinkler.setOn(true);
        drools.update($sprinkler);
        System.out.println( "Turn on the sprinkler for room " + $sprinkler.getRoom().getName() );
    }
}
