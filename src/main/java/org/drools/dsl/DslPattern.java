package org.drools.dsl;

public class DslPattern {

    private final String type;
    private final String id;
    private final String constraint;

    public DslPattern(String type, String id, String constraint) {
        this.type = type;
        this.id = id;
        this.constraint = constraint;
    }

    public String getConstraint() {
        return constraint;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return id + " : " + type + "( " + constraint + " )";
    }
}
