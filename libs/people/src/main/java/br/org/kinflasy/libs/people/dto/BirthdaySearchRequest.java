package br.org.kinflasy.libs.people.dto;

import java.time.MonthDay;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BirthdaySearchRequest {

    private List<UUID> ids;

    @NotNull
    private MonthDay start;

    @NotNull
    private MonthDay end;

}
