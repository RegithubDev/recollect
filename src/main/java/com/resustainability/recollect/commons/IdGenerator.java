package com.resustainability.recollect.commons;

import de.huxhorn.sulky.ulid.ULID;

public final class IdGenerator {
    private static final ULID ULID_GENERATOR = new ULID();
    private IdGenerator() {}
    public static String nextId() {
        return ULID_GENERATOR.nextULID();
    }
}
