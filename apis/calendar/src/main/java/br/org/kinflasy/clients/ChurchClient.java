package br.org.kinflasy.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.churches.dto.UnitDto;

@FeignClient(name = "calendar-churchesApi", url = "${CHURCHES_API_URL}", path = "churches")
public interface ChurchClient {

    @GetMapping("{id}/units")
    public List<UnitDto> listUnits(@PathVariable final UUID id);

}
