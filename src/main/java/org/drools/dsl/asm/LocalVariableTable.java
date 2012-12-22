package org.drools.dsl.asm;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LocalVariableTable {

    private final Map<Integer, String> indexes = new TreeMap<Integer, String>();
    private final Map<String, String> names = new HashMap<String, String>();

    public void addVar(String name, String type, int index) {
        indexes.put(index, name);
        names.put(name, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index : indexes.keySet()) {
            sb.append(index).append(" -> ");
            String name = indexes.get(index);
            sb.append(name).append(" -> ");
            sb.append(names.get(name)).append("\n");
        }
        return sb.toString();
    }

    public String getName(int index) {
        return indexes.get(index);
    }

    public String getType(int index) {
        return names.get(getName(index));
    }

    public String getType(String name) {
        return names.get(name);
    }
}
