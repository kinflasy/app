package br.org.kinflasy.api.contracts.contact;

import org.springframework.lang.NonNull;

public interface Emailable {

    public @NonNull String getEmail();

    public void setEmail(@NonNull String email);

}
