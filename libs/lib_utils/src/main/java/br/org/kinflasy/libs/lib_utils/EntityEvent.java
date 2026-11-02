package br.org.kinflasy.libs.lib_utils;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import br.org.kinflasy.libs.people.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class EntityEvent<D> implements ResolvableTypeProvider {

    private final D source;
    private UserDto user;

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Created<D> extends EntityEvent<D> {
        public Created(final D source) {
            super(source);
        }

        public Created(final D source, final UserDto user) {
            this(source);
            setUser(user);
        }
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Updated<D> extends EntityEvent<D> {
        private final D original;

        public Updated(final D original, final D modified) {
            super(modified);
            this.original = original;
        }

        public Updated(final D original, final D modified, final UserDto user) {
            this(original, modified);
            setUser(user);
        }
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Deleted<D> extends EntityEvent<D> {
        public Deleted(final D source) {
            super(source);
        }

        public Deleted(final D source, final UserDto user) {
            this(source);
            setUser(user);
        }
    }

}
