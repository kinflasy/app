package br.org.kinflasy.libs.people.dto;

import java.time.MonthDay;
import java.util.List;

import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BirthdaySearchRequest {

    private List<PersonId> ids;

    @NotNull
    private MonthDay start;

    @NotNull
    private MonthDay end;

}
