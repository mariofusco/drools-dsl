package org.drools.dsl.asm.insn;

import java.util.List;

public class LabelInstruction implements Instruction  {

    private final String label;

    public LabelInstruction(String label) {
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        List<EvaluationEnvironment> envs = ctx.suspendedEnvs.get(label);
        if (envs != null) {
            ctx.liveEnvs.addAll(envs);
        }
    }

    @Override
    public String toString() {
        return "LABEL " + label;
    }
}
