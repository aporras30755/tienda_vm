package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {

    // Se enlaza con la capa de servicio por medio de un CategoriaService
    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {

        var categorias = categoriaService.getCategorias(false);

        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());

        return "categoria/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Categoria categoria,
            BindingResult bindingResult,
            @RequestParam(value = "imagenFile", required = false)
            MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";

        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "categoria.error04";
        } else {
            try {
                categoriaService.save(categoria, imagenFile);

            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "categoria.error05";
            }
        }

        redirectAttributes.addFlashAttribute(
                titulo,
                messageSource.getMessage(
                        mensaje, null, Locale.getDefault()
                )
        );

        return "redirect:/categoria/listado";
    }
}
