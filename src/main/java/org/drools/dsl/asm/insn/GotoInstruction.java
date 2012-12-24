package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GotoInstruction implements Instruction  {

    private final String label;

    public GotoInstruction(String label) {
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        List<EvaluationEnvironment> envs = ctx.suspendedEnvs.get(label);
        if (envs == null) {
            envs = new ArrayList<EvaluationEnvironment>();
            ctx.suspendedEnvs.put(label, envs);
        }
        envs.addAll(ctx.liveEnvs);
        ctx.liveEnvs.clear();
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }
}