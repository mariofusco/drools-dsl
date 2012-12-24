package org.drools.dsl;

import java.util.ArrayList;
import java.util.List;

public class DslPattern implements Comparable<DslPattern> {

    private final String type;
    private final String id;
    private final List<String> constraints = new ArrayList<String>();

    public DslPattern(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public void addConstraint(String constraint) {
        constraints.add(constraint);
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
        return id + " : " + type + "( " + constraints + " )";
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
