package br.joliny.pedidos.services.impl;

import java.util.List;

public interface MethodsCRUD<DTO, O> {

   DTO create(O o);

   DTO update(Long id, DTO dto);

   void delete(Long id);

   DTO get(Long id);

   List<DTO> getAll();
}
