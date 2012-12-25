package org.drools.dsl.asm.insn;

import org.mvel2.asm.Type;

public class LoadConstantInstruction implements Instruction {

    private final Object cst;

    public LoadConstantInstruction(Object cst) {
        this.cst = cst;
    }

    @Override
    public String toString() {
        return "CONST " + cst;
    }

    public void process(ProcessingContext ctx) {
        for (EvaluationEnvironment env : ctx.liveEnvs) {
            if (cst instanceof String) {
                env.stack.push(new EvaluationEnvironment.Item("java.lang.String", "\"" + cst + "\""));
            } else if (cst instanceof Character) {
                env.stack.push(new EvaluationEnvironment.Item("char", "'" + cst + "'"));
            } else if (cst instanceof Type) {
                env.stack.push(new EvaluationEnvironment.Item("java.lang.Class", ((Type)cst).getClassName() + ".class"));
            } else {
                env.stack.push(new EvaluationEnvironment.Item(cst.getClass().getName(), cst.toString()));
            }
        }
    }
}
