package br.org.kinflasy.apis.auth.exceptions;

import br.org.kinflasy.apis.auth.migrations.contracts.OpenFGAMigration;

public class OpenFgaMigrationException extends RuntimeException {

    public OpenFgaMigrationException(final String message) {
        super(message);
    }

    public OpenFgaMigrationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OpenFgaMigrationException(final OpenFGAMigration migration) {
        this("Migration V" + migration.getVersion() + " falhou");
    }

    public OpenFgaMigrationException(final OpenFGAMigration migration, final Throwable e) {
        this("Migration V" + migration.getVersion() + " falhou", e);
    }

}
