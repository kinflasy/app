package br.org.kinflasy.libs.churches.events;

import br.org.kinflasy.libs.churches.dto.MembershipDto;
import lombok.Data;

public interface MembershipEvent {

    public MembershipDto getMembership();

    @Data
    public static class Created implements MembershipEvent {
        private final MembershipDto membership;
    }

}
