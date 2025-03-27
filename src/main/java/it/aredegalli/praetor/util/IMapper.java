package it.aredegalli.praetor.util;

public interface IMapper<M, D> {

    /**
     * Map Model to DTO
     *
     * @param mapper the model to map
     * @return the mapped DTO
     */
    D map(M mapper);

}
