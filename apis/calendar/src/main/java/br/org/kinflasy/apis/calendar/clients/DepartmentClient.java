package br.org.kinflasy.apis.calendar.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;

@FeignClient(name = "calendar-departmentsApi", url = "${CHURCHES_API_URL}", path = "churches/unit/departments")
public interface DepartmentClient {

    @GetMapping("{id}")
    public DepartmentDto findById(@PathVariable final UUID id);

}
