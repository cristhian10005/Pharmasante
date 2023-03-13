package com.pharmasante.pharmasanteProyect.EntitiesDto.IDto;

public interface IAccionRol<T,T2,R>{
    R retornoSencillo(T id);
    R retornoParametroId(T id, T id2);
    R retornoParametroBusqueda(T id, T2 valor);
}
