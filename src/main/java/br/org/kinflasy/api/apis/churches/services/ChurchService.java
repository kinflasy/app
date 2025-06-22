package br.org.kinflasy.api.apis.churches.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.entities.Church;
import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.churches.repositories.ChurchRepository;
import br.org.kinflasy.api.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.api.apis.people_filters.services.StaticPeopleFilterService;
import br.org.kinflasy.api.dto.core.church.ChurchDTO;
import br.org.kinflasy.api.dto.core.church.CreateStarterChurch;
import br.org.kinflasy.api.dto.core.church.CreateUnit;
import br.org.kinflasy.api.dto.core.church.department.CreateDepartment;
import br.org.kinflasy.api.libs.churches.enums.UnitType;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;
import br.org.kinflasy.api.libs.people_filters.enums.PersonCharacteristic;
import br.org.kinflasy.api.services.BaseService;

@Service
public class ChurchService extends BaseService<ChurchRepository, ChurchDTO, Church, UUID> {

    private final UnitService unitService;
    private final StaticPeopleFilterService filterService;

    protected ChurchService(@Autowired final ChurchRepository repository, @Autowired final UnitService unitService,
            @Autowired final StaticPeopleFilterService filterService) {
        super(repository);
        this.unitService = unitService;
        this.filterService = filterService;
    }

    @Override
    public UUID getId(final Church church) {
        return church.getId();
    }

    @Override
    public ChurchDTO toDto(final Church church) {
        return ChurchDTO.ofNonNull(church);
    }

    public List<Unit> getUnits(final UUID id) {
        return findById(id).getUnits();
    }

    public Church createStarter(final CreateStarterChurch form) {
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

    public Unit createUnit(final UUID id, final Unit unit) {
        unit.setChurch(findById(id));
        return unitService.create(unit);
    }

}
