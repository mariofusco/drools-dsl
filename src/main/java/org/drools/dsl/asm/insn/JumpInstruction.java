package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JumpInstruction implements Instruction {

    private final Comparison comparison;
    private final String label;

    public JumpInstruction(Comparison comparison, String label) {
        this.comparison = comparison;
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        for (Stack<String> stack : ctx.liveStacks) {
            String op1 = stack.pop();
            String op2 = stack.pop();

            Stack<String> clone = (Stack<String>)stack.clone();
            clone.push(op2 + " " + comparison.getSymbol() + " " + op1);
            List<Stack<String>> stacks = ctx.suspendedStacks.get(label);
            if (stacks == null) {
                stacks = new ArrayList<Stack<String>>();
                ctx.suspendedStacks.put(label, stacks);
            }
            stacks.add(clone);

            stack.push(op2 + " " + comparison.getOpposite().getSymbol() + " " + op1);
        }
    }

    @Override
    public String toString() {
        return "IF " + comparison + " JUMP " + label;
    }
}
