package br.org.kinflasy.apis.people.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.churches.dto.DeactivationRequest;
import br.org.kinflasy.libs.churches.dto.MembershipDto;

@FeignClient(name = "churchesApi", contextId = "people-churchesApi")
public interface ChurchClient {

    @PostMapping("deactivate-member")
    public List<MembershipDto> deactivateMember(@RequestBody final DeactivationRequest request);

}
