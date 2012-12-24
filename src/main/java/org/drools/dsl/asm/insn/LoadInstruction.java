package org.drools.dsl.asm.insn;

public class LoadInstruction implements Instruction {
    private final Class<?> ruleClass;
    private final int index;

    public LoadInstruction(Class<?> ruleClass, int index) {
        this.ruleClass = ruleClass;
        this.index = index;
    }

    @Override
    public String toString() {
        return "LOAD " + index;
    }

    public void process(ProcessingContext ctx) {
        for (EvaluationEnvironment env : ctx.liveEnvs) {
            if (index == 0) {
                env.stack.push(new EvaluationEnvironment.Item(ruleClass.getName(), "this"));
            } else {
                env.stack.push(env.heap.get(index));
            }
        }
    }
}
