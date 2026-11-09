package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService {

    // Se enlaza la capa de datos por medio de un objeto ProductoRepository
    private final ProductoRepository productoRepository;
    private final FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            // Si solo quiero los productos activos...
            return productoRepository.findByActivoTrue();
        } else {
            // Quiero todos los registros de la tabla producto...
            return productoRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    // Si idProducto tiene un valor... se hace un update, si no tiene es un insert
    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {

        producto = productoRepository.save(producto);

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile,
                        "producto",
                        producto.getIdProducto()
                );

                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);

            } catch (Exception e) {
                log.warn("No fue posible almacenar la imagen del producto: "
                        + producto.getIdProducto(), e);
            }
        }
    }

    // Si idProducto no existe se lanza un error, sino se intenta eliminar
    @Transactional
    public void delete(Integer idProducto) {

        if (!productoRepository.existsById(idProducto)) {
            throw new IllegalArgumentException(
                    "El producto no existe: " + idProducto
            );
        }

        try {
            productoRepository.deleteById(idProducto);
            productoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el producto, tiene datos asociados: "
                    + idProducto
            );
        }
    }

    private static class FirebaseStorageService {

        public FirebaseStorageService() {
        }

        private String uploadImage(MultipartFile imagenFile, String producto, Integer idProducto) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}