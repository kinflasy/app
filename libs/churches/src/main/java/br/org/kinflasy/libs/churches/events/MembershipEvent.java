package br.org.kinflasy.libs.churches.events;

import br.org.kinflasy.libs.churches.dto.MembershipDto;
import lombok.Data;

@Data
public abstract class MembershipEvent {

    private final MembershipDto membership;

    public static class Created extends MembershipEvent {
        public Created(final MembershipDto membership) {
            super(membership);
        }
    }

}
