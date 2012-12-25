package org.drools.dsl;

import org.drools.spi.KnowledgeHelper;

import java.lang.reflect.Field;

public abstract class AbstractJavaRule {
    protected KnowledgeHelper drools;

    public void setDrools(KnowledgeHelper drools) {
        this.drools = drools;
    }

    public abstract boolean lhs();

    public abstract void rhs();

    public final void setValue(String name, Object value) {
        try {
            Field field = getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(this, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected final boolean not(Class<?> value) {
        return false;
    }

    protected final boolean not(boolean value) {
        return value;
    }

    protected final boolean exists(Class<?> value) {
        return false;
    }

    protected final boolean exists(boolean value) {
        return value;
    }
}
