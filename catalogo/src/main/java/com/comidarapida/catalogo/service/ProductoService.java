package com.comidarapida.catalogo.service;

import com.comidarapida.catalogo.client.InventarioClient;
import com.comidarapida.catalogo.dto.ProductoDTO;
import com.comidarapida.catalogo.dto.UsuarioResponseDTO;
import com.comidarapida.catalogo.model.Producto;
import com.comidarapida.catalogo.repository.ProductoRepository;
import com.comidarapida.catalogo.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private InventarioClient inventarioClient;

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

    // --- MÉTODO NUEVO PARA DATAAFAKER ---
    public void generarDatosFalsos(int cantidad) {
        Faker faker = new Faker(new Locale("es"));
        List<Producto> nuevosProductos = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Producto producto = new Producto();
            // Genera nombres como "Sushi", "Pizza", etc.
            producto.setNombre(faker.food().dish());
            // Genera descripciones mezclando ingredientes y especias
            producto.setDescripcion("Delicioso plato con " + faker.food().ingredient() + " y un toque de " + faker.food().spice());
            // Genera un precio aleatorio entre 3000 y 15000
            producto.setPrecio((double) faker.number().numberBetween(3000, 15000));

            nuevosProductos.add(producto);
        }

        // Guardamos toda la lista de golpe en la base de datos por eficiencia
        productoRepository.saveAll(nuevosProductos);
    }

    // --- MÉTODO PRIVADO AL FINAL ---
    private ProductoDTO convertirADTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }
}