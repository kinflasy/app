package br.org.kinflasy.libs.churches.enums.department;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Extension {

    SOMA("Soma", "Gerenciamento de Membresia e Departamentos");

    private final String name;
    private final String description;

}
