package org.drools.dsl.asm.insn;

public enum Comparison {

    EQ("=="), NE("!="), GT(">"), GE(">="), LT("<"), LE("<=");

    private final String symbol;

    Comparison(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isEquality() {
        return this == EQ || this == NE;
    }

    public Comparison getOpposite() {
        switch(this) {
            case EQ: return NE;
            case NE: return EQ;
            case GT: return LE;
            case GE: return LT;
            case LT: return GE;
            case LE: return GT;
        }
        return null;
    }
}
