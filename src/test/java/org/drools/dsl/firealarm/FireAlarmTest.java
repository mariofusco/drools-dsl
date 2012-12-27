package org.drools.dsl.firealarm;

import org.drools.dsl.DslPackageBuilder;
import org.drools.dsl.firealarm.model.Fire;
import org.drools.dsl.firealarm.model.Room;
import org.drools.dsl.firealarm.model.Sprinkler;
import org.junit.Test;
import org.kie.KnowledgeBase;
import org.kie.KnowledgeBaseFactory;
import org.kie.runtime.StatefulKnowledgeSession;
import org.kie.runtime.rule.FactHandle;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class FireAlarmTest {

    @Test
    public void testFireAlarm() {
        checkFireAlarm(new DslPackageBuilder().addRule(FireAlarmRules.class));
    }

    @Test
    public void testFireAlarmInPackage() {
        checkFireAlarm(new DslPackageBuilder().addRulesInPackage("org.drools.dsl.firealarm.rules"));
    }

    private void checkFireAlarm(DslPackageBuilder dslPackageBuilder) {
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
        FactHandle fact = ksession.insert(new Fire(rooms[roomNr]));
        assertEquals(2, ksession.fireAllRules());
        ksession.retract(fact);
        assertEquals(3, ksession.fireAllRules());
    }
}
