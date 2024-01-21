package br.org.kinflasy.api.services.core.church;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.ChurchDTO;
import br.org.kinflasy.api.dto.core.church.CreateStarterChurch;
import br.org.kinflasy.api.dto.core.church.CreateUnit;
import br.org.kinflasy.api.dto.core.church.department.CreateDepartment;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peopleFilter.StaticPeopleFilter;
import br.org.kinflasy.api.repositories.core.church.ChurchRepository;
import br.org.kinflasy.api.services.BaseService;
import br.org.kinflasy.api.services.core.peopleFilter.StaticPeopleFilterService;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

@Service
public class ChurchService extends BaseService<ChurchRepository, ChurchDTO, Church, Integer> {

    private final UnitService unitService;
    private final StaticPeopleFilterService filterService;

    protected ChurchService(@Autowired final ChurchRepository repository, @Autowired final UnitService unitService,
            @Autowired final StaticPeopleFilterService filterService) {
        super(repository);
        this.unitService = unitService;
        this.filterService = filterService;
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

    public @NonNull Church createStarter(final @NonNull CreateStarterChurch form) {
        // Criar a Igreja
        final var church = create(form.toChurch());

        // Criar a Unidade vinculada à Igreja
        final var unit = unitService.create(
                new CreateUnit("Sede", "sede", church.getPhone(), church.getEmail(), UnitType.MAIN, form.getAddress())
                        .toUnit(church));
        church.setUnits(new ArrayList<>(List.of(unit)));

        // Visibilidade padrão
        final var everybody = filterService.findOrCreate(new StaticPeopleFilter(PersonCharacteristic.EVERYBODY));

        // Departamentos
        final List<Department> departments = List.of(
                new CreateDepartment("Ministério Pastoral", "pastoral", DepartmentType.ADMINISTRATIVE),
                new CreateDepartment("Secretaria", "secretaria", DepartmentType.ADMINISTRATIVE),
                new CreateDepartment("Tesouraria", "tesouraria", DepartmentType.ADMINISTRATIVE),
                new CreateDepartment("Ministério de Louvor", "louvor", DepartmentType.MINISTRY),
                new CreateDepartment("Ministério de Mídia", "midia", DepartmentType.MINISTRY),
                new CreateDepartment("Ministério Jovem", "jovem", DepartmentType.MINISTRY)).stream()
                .map(departmentForm -> {
                    final var department = departmentForm.toDepartment();
                    department.setUnit(unit);
                    department.setVisibilityFilter(everybody);
                    return department;
                }).toList();

        unit.setDepartments(new ArrayList<>(departments));

        return findById(church.getId());
    }

    public @NonNull Unit createUnit(final @NonNull Integer id, final @NonNull Unit unit) {
        unit.setChurch(findById(id));
        return unitService.create(unit);
    }

}
