package com.tensorzero.typeinference;

/**
 * Represents nodes in the type tree, including primitives, tuples, functions, and type variables.
 */
public abstract class TypeNode {

    /**
     * Return the human-readable signature for the type.
     *
     * Examples: "int", "(int, str)", "T", "(T, int) -> T"
     */
    public abstract String getTypeSignature();
}
