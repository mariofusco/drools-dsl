package org.drools.dsl.asm.insn;

import org.drools.dsl.asm.MethodArgsDescr;

import java.util.Arrays;
import java.util.Stack;

public class MethodInvocationInstruction implements Instruction {

    private final String owner;
    private final String name;
    private final MethodArgsDescr methodArgsDescr;

    public MethodInvocationInstruction(String owner, String name, MethodArgsDescr methodArgsDescr) {
        this.owner = owner;
        this.name = name;
        this.methodArgsDescr = methodArgsDescr;
    }

    @Override
    public String toString() {
        return "INVOKE " + methodArgsDescr.getReturn() + " " + owner + "." + name + "(" + Arrays.toString(methodArgsDescr.getArgs()) + ")";
    }

    public void process(ProcessingContext ctx) {
        for (Stack<String> stack : ctx.liveStacks) {
            String[] args = new String[methodArgsDescr.getArgs().length];
            for (int i = 0; i < args.length; i++) {
                args[i] = stack.pop();
            }
            String invoked = stack.pop();

            StringBuilder sb = new StringBuilder();
            sb.append(invoked).append(".").append(name).append("(");
            boolean first = true;
            for (String arg : args) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(arg);
            }
            sb.append(")");

            stack.push(sb.toString());
        }
    }

}
