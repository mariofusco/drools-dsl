package org.drools.dsl;

import java.util.ArrayList;
import java.util.List;

public class DslPattern implements Comparable<DslPattern> {

    public enum Modifier {
        NONE, NOT, EXISTS;
    }

    private final String type;
    private final String id;
    private final List<String> constraints = new ArrayList<String>();
    private Modifier modifier = Modifier.NONE;

    public DslPattern(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public DslPattern setModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public boolean isBound() {
        return modifier == Modifier.NONE;
    }

    public DslPattern addConstraint(String constraint) {
        constraints.add(constraint);
        return this;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        switch (modifier) {
            case NOT:
                return "not " + type + "( " + constraints + " )";
            case EXISTS:
                return "exists " + type + "( " + constraints + " )";
            case NONE:
            default:
                return id + " : " + type + "( " + constraints + " )";
        }
    }

    public int compareTo(DslPattern o) {
        for (String constraint : constraints) {
            if (constraint.contains(o.id)) {
                return 1;
            }
        }
        for (String constraint : o.constraints) {
            if (constraint.contains(id)) {
                return -1;
            }
        }
        return 0;
    }
}
