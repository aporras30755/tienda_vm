package com.tienda.controller;

import com.tienda.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {

    //Se enlaza con la capa de servicio por medio de un CategoriaService
    private final CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias=categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "categoria/listado";
    }
}