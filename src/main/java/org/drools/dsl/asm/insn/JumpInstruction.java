package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.List;

public class JumpInstruction implements Instruction {

    private final Comparison comparison;
    private final String label;

    public JumpInstruction(Comparison comparison, String label) {
        this.comparison = comparison;
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        for (EvaluationEnvironment env : ctx.liveEnvs) {
            EvaluationEnvironment.Item op1 = env.stack.pop();
            EvaluationEnvironment.Item op2 = env.stack.pop();

            EvaluationEnvironment clone = env.clone();
            if (comparison.isEquality() && op2.type.equals("boolean") && (op1.value.equals("0") || op1.value.equals("1"))) {
                if (comparison == Comparison.EQ ^ op1.value.equals("1")) {
                    clone.stack.push(new EvaluationEnvironment.Item("boolean", "!" + op2.value));
                    env.stack.push(new EvaluationEnvironment.Item("boolean", op2.value));
                } else {
                    clone.stack.push(new EvaluationEnvironment.Item("boolean", op2.value));
                    env.stack.push(new EvaluationEnvironment.Item("boolean", "!" + op2.value));
                }
            } else {
                clone.stack.push(new EvaluationEnvironment.Item("boolean", op2.value + " " + comparison.getSymbol() + " " + op1.value));
                env.stack.push(new EvaluationEnvironment.Item("boolean", op2.value + " " + comparison.getOpposite().getSymbol() + " " + op1.value));
            }
            List<EvaluationEnvironment> envs = ctx.suspendedEnvs.get(label);
            if (envs == null) {
                envs = new ArrayList<EvaluationEnvironment>();
                ctx.suspendedEnvs.put(label, envs);
            }
            envs.add(clone);
        }
    }

    @Override
    public String toString() {
        return "IF " + comparison + " JUMP " + label;
    }
}
