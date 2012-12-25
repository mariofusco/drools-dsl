package org.drools.dsl.firealarm.rules;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Fire;
import org.drools.dsl.firealarm.model.Room;
import org.drools.dsl.firealarm.model.Sprinkler;

public class WhenTheFireIsGoneTurnOffTheSprinkler extends AbstractJavaRule {

    private Fire $fire;
    private Sprinkler $sprinkler;
    private Room $room;

    @Override
    public boolean lhs() {
        return $sprinkler.getRoom().equals($room) && $sprinkler.isOn() && not($fire.getRoom().equals($room));
    }

    @Override
    public void rhs() {
        $sprinkler.setOn(false);
        drools.update($sprinkler);
        System.out.println( "Turn off the sprinkler for room " + $room.getName() );
    }
}
