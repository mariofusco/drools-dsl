package org.drools.dsl.asm.insn;

public class StoreInstruction implements Instruction {
    private final int index;

    public StoreInstruction(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "LOAD " + index;
    }

    public void process(ProcessingContext ctx) {
        for (EvaluationEnvironment env : ctx.liveEnvs) {
            env.heap.put(index, env.stack.pop());
        }
    }
}
