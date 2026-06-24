package com.restaurante.ms_pedidos.service;

import com.restaurante.ms_pedidos.client.CatalogoClient;
import com.restaurante.ms_pedidos.dto.request.ItemPedidoRequestDTO;
import com.restaurante.ms_pedidos.dto.request.PedidoRequestDTO;
import com.restaurante.ms_pedidos.dto.response.*;
import com.restaurante.ms_pedidos.model.ItemPedido;
import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoClient catalogoClient;
    //private final PedidoProducer pedidoProducer;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        log.info("Iniciando creación de pedido para: {}", dto.getNombreCliente());

        List<ItemPedido> items = dto.getItems().stream()
                .map(itemDto -> construirItem(itemDto))
                .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = Pedido.builder()
                .nombreCliente(dto.getNombreCliente())
                .estado("RECIBIDO")
                .total(total)
                .fechaCreacion(LocalDateTime.now())
                .items(items)
                .build();

        items.forEach(item -> item.setPedido(pedido));
        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido creado con id: {}", guardado.getId());

        //pedidoProducer.publicarPedido(mapToEvent(guardado));

        return mapToResponse(guardado);
    }

    private ItemPedido construirItem(ItemPedidoRequestDTO itemDto) {
        log.info("Consultando producto {} en ms-catalogo", itemDto.getProductoId());

        ProductoCatalogoDTO producto = catalogoClient
                .obtenerProductoPorId(itemDto.getProductoId());

        if (!producto.getDisponible()) {
            throw new RuntimeException("Producto no disponible: "
                    + producto.getNombre());
        }

        BigDecimal subtotal = producto.getPrecio()
                .multiply(BigDecimal.valueOf(itemDto.getCantidad()));

        return ItemPedido.builder()
                .productoId(producto.getId())
                .nombreProducto(producto.getNombre())
                .cantidad(itemDto.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();
    }

    @Override
    public PedidoResponseDTO buscarPorId(Long id) {
        log.info("Buscando pedido id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
        return mapToResponse(pedido);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        log.info("Listando todos los pedidos");
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO mapToResponse(Pedido p) {
        List<ItemPedidoResponseDTO> itemsDTO = p.getItems().stream()
                .map(i -> ItemPedidoResponseDTO.builder()
                        .productoId(i.getProductoId())
                        .nombreProducto(i.getNombreProducto())
                        .cantidad(i.getCantidad())
                        .precioUnitario(i.getPrecioUnitario())
                        .subtotal(i.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return PedidoResponseDTO.builder()
                .id(p.getId())
                .nombreCliente(p.getNombreCliente())
                .estado(p.getEstado())
                .total(p.getTotal())
                .fechaCreacion(p.getFechaCreacion())
                .items(itemsDTO)
                .build();
    }


}
