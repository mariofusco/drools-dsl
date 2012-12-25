package org.drools.dsl.firealarm;

import org.drools.dsl.DslPackageBuilder;
import org.drools.dsl.firealarm.model.Fire;
import org.drools.dsl.firealarm.model.Room;
import org.drools.dsl.firealarm.model.Sprinkler;
import org.drools.dsl.firealarm.rules.CancelTheAlarmWhenAllTheFiresHaveGone;
import org.drools.dsl.firealarm.rules.RaiseTheAlarmWhenWeHaveOneOrMoreFires;
import org.drools.dsl.firealarm.rules.StatusOutputWhenThingsAreOk;
import org.drools.dsl.firealarm.rules.WhenTheFireIsGoneTurnOffTheSprinkler;
import org.drools.dsl.firealarm.rules.WhenThereIsAFireTurnOnTheSprinkler;
import org.junit.Test;
import org.kie.KnowledgeBase;
import org.kie.KnowledgeBaseFactory;
import org.kie.runtime.StatefulKnowledgeSession;
import org.kie.runtime.rule.FactHandle;

import java.util.Random;

public class FireAlarmTest {

    @Test
    public void testFireAlarm() {

        DslPackageBuilder dslPackageBuilder = new DslPackageBuilder()
                .addRule( WhenThereIsAFireTurnOnTheSprinkler.class,
                          WhenTheFireIsGoneTurnOffTheSprinkler.class,
                          RaiseTheAlarmWhenWeHaveOneOrMoreFires.class,
                          CancelTheAlarmWhenAllTheFiresHaveGone.class,
                          StatusOutputWhenThingsAreOk.class );

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( dslPackageBuilder.getKnowledgePackages() );
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        int roomsNumber = 3;

        // init
        Room[] rooms = new Room[roomsNumber];
        for (int i = 0; i < rooms.length; i++) {
            Room room = new Room("Room" + i);
            ksession.insert(room);
            Sprinkler sprinkler = new Sprinkler(room);
            ksession.insert(sprinkler);
            rooms[i] = room;
        }

        // go!
        Random random = new Random();
        int roomNr = random.nextInt(roomsNumber);
        FactHandle fact = ksession.insert(new Fire(rooms[roomNr]));;
        ksession.fireAllRules();
        ksession.retract(fact);
        ksession.fireAllRules();
    }
}
