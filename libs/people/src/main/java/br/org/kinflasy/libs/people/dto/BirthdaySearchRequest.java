package br.org.kinflasy.libs.people.dto;

import java.time.MonthDay;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BirthdaySearchRequest {

    private List<UUID> ids;

    @NotNull
    private MonthDay start;

    @NotNull
    private MonthDay end;

}
