package br.org.kinflasy.api.services.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.ChurchDTO;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.repositories.core.church.ChurchRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class ChurchService extends BaseService<ChurchRepository, ChurchDTO, Church, Integer> {

    private final UnitService unitService;

    protected ChurchService(@Autowired final ChurchRepository repository, @Autowired final UnitService unitService) {
        super(repository);
        this.unitService = unitService;
    }

    @Override
    public @NonNull Integer getId(final @NonNull Church church) {
        return church.getId();
    }

    @Override
    public @Nullable ChurchDTO toNullableDTO(final @Nullable Church church) {
        return ChurchDTO.ofNullable(church);
    }

    @Override
    public @NonNull ChurchDTO toNonNullDTO(final @NonNull Church church) {
        return ChurchDTO.ofNonNull(church);
    }

    public @NonNull List<Unit> getUnits(final @NonNull Integer id) {
        return findById(id).getUnits();
    }

    public @NonNull Unit createUnit(final @NonNull Integer id, final @NonNull Unit unit) {
        unit.setChurch(findById(id));
        return unitService.create(unit);
    }

}
