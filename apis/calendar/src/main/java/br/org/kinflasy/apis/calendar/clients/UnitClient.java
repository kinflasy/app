package br.org.kinflasy.apis.calendar.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;

@FeignClient(name = "calendar-unitsApi", url = "${CHURCHES_API_URL}", path = "church/units")
public interface UnitClient {

    @GetMapping
    public List<MembershipDto.DetailingUnit> listByLoggedUser();

    @GetMapping("{id}")
    public UnitDto.Detailed findById(@PathVariable UUID id);

}
