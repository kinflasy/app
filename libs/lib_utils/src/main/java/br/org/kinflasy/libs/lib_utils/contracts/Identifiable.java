package br.org.kinflasy.libs.lib_utils.contracts;

import java.io.Serializable;
import java.util.UUID;

public interface Identifiable extends Serializable, Comparable<Identifiable> {

    UUID get();

}
