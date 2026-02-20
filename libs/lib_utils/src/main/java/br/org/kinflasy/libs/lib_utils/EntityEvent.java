package br.org.kinflasy.libs.lib_utils;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public abstract class EntityEvent<D> implements ResolvableTypeProvider {

    private final D source;

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Created<D> extends EntityEvent<D> {
        public Created(final D source) {
            super(source);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Updated<D> extends EntityEvent<D> {
        private final D original;

        public Updated(final D original, final D modified) {
            super(modified);
            this.original = original;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Deleted<D> extends EntityEvent<D> {
        public Deleted(final D source) {
            super(source);
        }
    }

}
