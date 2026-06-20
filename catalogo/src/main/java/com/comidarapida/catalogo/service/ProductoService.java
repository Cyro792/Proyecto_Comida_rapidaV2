package com.comidarapida.catalogo.service;

import com.comidarapida.catalogo.client.InventarioClient;
import com.comidarapida.catalogo.dto.ProductoDTO;
import com.comidarapida.catalogo.dto.UsuarioResponseDTO;
import com.comidarapida.catalogo.model.Producto;
import com.comidarapida.catalogo.repository.ProductoRepository;
import com.comidarapida.catalogo.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final UsuarioClient usuarioClient;


    public UsuarioResponseDTO probarConexionConUsuarios(Long idUsuario){
        UsuarioResponseDTO usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
        System.out.println("Catálogo encontró al usuario: " + usuario.getNombre());

        return usuario;
    }

    @Autowired
    private InventarioClient inventarioClient; // Tu nuevo puente al puerto 8083


    public List<Object> traerInventario() {
        return inventarioClient.obtenerProductosDelInventario().getBody();
    }

    public List<ProductoDTO> obtenerTodos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::convertirADTO).toList();
    }

    public ProductoDTO guardarProducto(ProductoDTO productoDTO){
        Producto nuevaEntidad = new Producto();
        nuevaEntidad.setNombre(productoDTO.getNombre());
        nuevaEntidad.setDescripcion(productoDTO.getDescripcion());
        nuevaEntidad.setPrecio(productoDTO.getPrecio());

        Producto productoGuardado = productoRepository.save(nuevaEntidad);
        return convertirADTO(productoGuardado);
    }

    private ProductoDTO convertirADTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO){
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error!! El producto no existe"));

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());

        Producto productorActualizado = productoRepository.save(productoExistente);
        return convertirADTO(productorActualizado);
    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }
}