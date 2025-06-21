package br.org.kinflasy.api.services.core.church.membership;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.membership.LeaveDTO;
import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.repositories.core.church.membership.LeaveRepository;
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
    public LeaveDTO toNullableDTO(final Leave leave) {
        return LeaveDTO.ofNullable(leave);
    }

    @Override
    public LeaveDTO toNonNullDTO(final Leave leave) {
        return LeaveDTO.ofNonNull(leave);
    }

}
