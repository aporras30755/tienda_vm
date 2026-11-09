package com.tienda.service;

import com.tienda.domain.Categoria;
import com.tienda.repository.CategoriaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    //Se enlaza la capa de datos por medio de un objeto CategoriaRepository
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly=true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) { //Si sólo quiero las categorías activas...
            return categoriaRepository.findByActivoTrue();
        } else { //Quiero todos los registros de la tabla categoria...
            return categoriaRepository.findAll();
        }
    }
}