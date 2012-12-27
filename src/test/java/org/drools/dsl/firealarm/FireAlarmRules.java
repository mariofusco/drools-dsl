package org.drools.dsl.firealarm;

import org.drools.dsl.AbstractJavaRule;
import org.drools.dsl.firealarm.model.Alarm;
import org.drools.dsl.firealarm.model.Fire;
import org.drools.dsl.firealarm.model.Room;
import org.drools.dsl.firealarm.model.Sprinkler;

public class FireAlarmRules {
    public static class WhenThereIsAFireTurnOnTheSprinkler extends AbstractJavaRule {

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

    public static class WhenTheFireIsGoneTurnOffTheSprinkler extends AbstractJavaRule {

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

    public static class RaiseTheAlarmWhenWeHaveOneOrMoreFires extends AbstractJavaRule {

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

    public static class CancelTheAlarmWhenAllTheFiresHaveGone extends AbstractJavaRule {

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

    public static class StatusOutputWhenThingsAreOk extends AbstractJavaRule {

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
}
