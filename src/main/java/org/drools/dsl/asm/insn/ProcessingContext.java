package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingContext {
    final List<EvaluationEnvironment> liveEnvs = new ArrayList<EvaluationEnvironment>();
    final List<EvaluationEnvironment> terminatedEnvs = new ArrayList<EvaluationEnvironment>();
    final Map<String, List<EvaluationEnvironment>> suspendedEnvs = new HashMap<String, List<EvaluationEnvironment>>();

    private final Map<String, String> vars;

    public ProcessingContext(Map<String, String> vars) {
        this.vars = vars;
        liveEnvs.add(new EvaluationEnvironment());
    }

    public List<EvaluationEnvironment> getTerminatedEnvs() {
        return terminatedEnvs;
    }
}
