package br.org.kinflasy.api.services.core.church;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.repositories.core.church.UnitRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class UnitService extends BaseService<UnitRepository, UnitDTO, Unit, Integer> {

    protected UnitService(@Autowired final UnitRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Unit unit) {
        return unit.getId();
    }

    @Override
    public @Nullable UnitDTO toNullableDTO(final @Nullable Unit unit) {
        return UnitDTO.ofNullable(unit);
    }

    @Override
    public @NonNull UnitDTO toNonNullDTO(final @NonNull Unit unit) {
        return UnitDTO.ofNonNull(unit);
    }
}