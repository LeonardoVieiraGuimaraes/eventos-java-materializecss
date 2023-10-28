package com.evento.evento.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evento.evento.models.ConvidadoModel;
import com.evento.evento.models.EventoModel;

@Repository
public interface ConvidadoRepository extends CrudRepository<ConvidadoModel, String> {

    Iterable<ConvidadoModel> findByEvento(EventoModel evento);

    ConvidadoModel findByCpf(String cpf);
}
