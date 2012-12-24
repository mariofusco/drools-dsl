package org.drools.dsl.asm.insn;

public class GetFieldInstruction implements Instruction {

    private final String name;
    private final String owner;
    private final String type;

    public GetFieldInstruction(String name, String owner, String type) {
        this.name = name;
        this.owner = owner;
        this.type = type;
    }

    public void process(ProcessingContext ctx) {
        for (EvaluationEnvironment env : ctx.liveEnvs) {
            String owner = env.stack.pop().value;
            if (owner.equals("this")) {
                env.stack.push(new EvaluationEnvironment.Item(type, name));
            } else {
                env.stack.push(new EvaluationEnvironment.Item(type, owner + "." + name));
            }
        }
    }

    @Override
    public String toString() {
        return "GET " + owner + "." + name;
    }
}
