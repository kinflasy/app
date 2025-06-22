package br.org.kinflasy.api.apis.churches.services.membership;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.entities.membership.Leave;
import br.org.kinflasy.api.apis.churches.repositories.membership.LeaveRepository;
import br.org.kinflasy.api.dto.core.church.membership.LeaveDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class LeaveService extends BaseService<LeaveRepository, LeaveDTO, Leave, UUID> {

    protected LeaveService(@Autowired final LeaveRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final Leave leave) {
        return leave.getId();
    }

    @Override
    public LeaveDTO toDto(final Leave leave) {
        return LeaveDTO.ofNonNull(leave);
    }

}
