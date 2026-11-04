package br.org.kinflasy.apis.churches.converters.department;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.entities.department.Department;
import br.org.kinflasy.libs.api_utils.Converter;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;

@Component
public class DepartmentConverter extends Converter<Department, DepartmentDto> {

    public DepartmentConverter(final ModelMapper mapper) {
        super(mapper, Department.class, DepartmentDto.class);
    }

}
