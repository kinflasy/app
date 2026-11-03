package br.org.kinflasy.libs.lib_utils;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import br.org.kinflasy.libs.lib_utils.contracts.Identifiable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractId implements Identifiable {

    @JsonValue
    private final UUID value;

    protected AbstractId(final UUID value) {
        this.value = value;
    }

    @Override
    public UUID get() {
        return value;
    }

    @Override
    public int compareTo(final Identifiable other) {
        return value.compareTo(other.get());
    }

}
