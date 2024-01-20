package br.org.kinflasy.api.services.core.church.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.membership.LeaveDTO;
import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.repositories.core.church.membership.LeaveRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class LeaveService extends BaseService<LeaveRepository, LeaveDTO, Leave, Integer> {

    protected LeaveService(@Autowired final LeaveRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Leave leave) {
        return leave.getId();
    }

    @Override
    public @Nullable LeaveDTO toNullableDTO(final @Nullable Leave leave) {
        return LeaveDTO.ofNullable(leave);
    }

    @Override
    public @NonNull LeaveDTO toNonNullDTO(final @NonNull Leave leave) {
        return LeaveDTO.ofNonNull(leave);
    }

}
