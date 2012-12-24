package org.drools.dsl.asm.insn;

import java.util.HashMap;
import java.util.Stack;

public class EvaluationEnvironment implements Cloneable {
    final Stack<Item> stack;
    final HashMap<Integer, Item> heap;

    public EvaluationEnvironment() {
        this(new Stack<Item>(), new HashMap<Integer, Item>());
    }

    public EvaluationEnvironment(Stack<Item> stack, HashMap<Integer, Item> heap) {
        this.stack = stack;
        this.heap = heap;
    }

    @Override
    public EvaluationEnvironment clone() {
        return new EvaluationEnvironment((Stack<Item>)stack.clone(), (HashMap<Integer, Item>)heap.clone());
    }

    public Stack<Item> getStack() {
        return stack;
    }

    public static class Item {
        final String type;
        final String value;

        public Item(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return type + " " + value;
        }
    }
}
