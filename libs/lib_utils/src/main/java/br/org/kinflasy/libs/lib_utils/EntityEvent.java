package br.org.kinflasy.libs.lib_utils;

import lombok.Data;

public interface EntityEvent<D> {

    D getDto();

    @Data
    public static class Created<D> implements EntityEvent<D> {
        private final D dto;
    }

    @Data
    public static class Updated<D> implements EntityEvent<D> {
        private final D original;
        private final D modified;

        @Override
        public D getDto() {
            return modified;
        }
    }

    @Data
    public static class Deleted<D> implements EntityEvent<D> {
        private final D dto;
    }

}
