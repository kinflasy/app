package br.org.kinflasy.libs.lib_utils.contracts;

import java.util.Set;
import java.util.stream.Stream;

public interface HierarchyEnum<E extends Enum<E> & HierarchyEnum<E>> {

    Set<E> getIncludedValues();

    default boolean includes(E value) {
        return getIncludedValues().contains(value);
    }

    default Set<E> flatIncludedValues(final E[] values) {
        final var includedValues = Set.of(values);

        Stream.of(values)
                .forEach(value -> includedValues.addAll(value.getIncludedValues()));

        return includedValues;
    }

}
