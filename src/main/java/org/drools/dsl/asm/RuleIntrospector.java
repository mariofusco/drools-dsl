package org.drools.dsl.asm;

import org.drools.dsl.DslPattern;
import org.drools.dsl.asm.insn.EvaluationEnvironment;
import org.drools.dsl.asm.insn.Instruction;
import org.drools.dsl.asm.insn.ProcessingContext;
import org.mvel2.asm.AnnotationVisitor;
import org.mvel2.asm.Attribute;
import org.mvel2.asm.ClassReader;
import org.mvel2.asm.ClassVisitor;
import org.mvel2.asm.FieldVisitor;
import org.mvel2.asm.MethodVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.mvel2.asm.Type.getType;

public class RuleIntrospector implements ClassVisitor {

    private final Class<?> ruleClass;

    private LhsVisitor lhsVisitor;

    private final Map<String, String> vars = new HashMap<String, String>();

    private final Map<String, DslPattern> dslPatterns = new HashMap<String, DslPattern>();

    public RuleIntrospector(Class<?> ruleClass) {
        this.ruleClass = ruleClass;
        introspect();
    }

    private void introspect() {
        try {
            new ClassReader(ruleClass.getName()).accept(this, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<DslPattern> getDslPatterns() {
        List<DslPattern> list = new ArrayList<DslPattern>(dslPatterns.values());
        Collections.sort(list);
        return list;
    }

    public Map<String, String> getVars() {
        return vars;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    }

    public void visitSource(String source, String debug) { }

    public void visitOuterClass(String owner, String name, String desc) { }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) { }

    public void visitInnerClass(String name, String outerName, String innerName, int access) { }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        vars.put(name, getType(desc).getClassName());
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("lhs") && desc.equals("()Z")) {
            lhsVisitor = new LhsVisitor(ruleClass);
            return lhsVisitor;
        }
        return null;
    }

    public void visitEnd() {
        ProcessingContext processingContext = new ProcessingContext(vars);
        for (Instruction instruction : lhsVisitor.getInstructions()) {
            instruction.process(processingContext);
        }

        for (Map.Entry<String, String> var : vars.entrySet()) {
            dslPatterns.put(var.getKey(), new DslPattern(var.getValue(), var.getKey()));
        }

        for (EvaluationEnvironment env : processingContext.getTerminatedEnvs()) {
            Stack<EvaluationEnvironment.Item> stack = env.getStack();
            String result = stack.pop().getValue();
            if (result.equals("0")) {
                result = null;
                continue;
            }
            if (result.equals("1")) {
                result = stack.pop().getValue();
            }

            addDslPattern(result);
            while (!stack.isEmpty()) {
                addDslPattern(stack.pop().getValue());
            }
        }
    }

    private void addDslPattern(String result) {
        System.out.println(result);
        int firstDot = result.indexOf('.');
        String id = result.substring(0, firstDot);
        String negation = "";
        if (id.startsWith("!")) {
            negation = "!";
            id = id.substring(1);
        }
        String constraint = result.substring(firstDot + 1);
        dslPatterns.get(id).addConstraint(negation + constraint);
    }
}
