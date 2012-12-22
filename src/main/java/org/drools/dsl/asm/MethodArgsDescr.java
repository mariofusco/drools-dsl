package org.drools.dsl.asm;

import org.mvel2.asm.Type;

import static org.mvel2.asm.Type.getArgumentTypes;
import static org.mvel2.asm.Type.getReturnType;

public class MethodArgsDescr {

    private final String descr;

    private String[] args;
    private String ret;

    public MethodArgsDescr(String descr) {
        this.descr = descr;
        init();
    }

    private void init() {
        Type[] argumentTypes = getArgumentTypes(descr);
        args = new String[argumentTypes.length];
        int i = 0;
        for (Type argumentType : argumentTypes) {
            args[i++] = argumentType.getClassName();
        }

        ret = getReturnType(descr).getClassName();
    }

    public String[] getArgs() {
        return args;
    }

    public String getReturn() {
        return ret;
    }
}
