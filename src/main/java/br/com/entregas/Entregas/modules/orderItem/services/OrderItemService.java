package br.com.entregas.Entregas.modules.orderItem.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.models.OrderModel;
import br.com.entregas.Entregas.modules.order.repositories.OrderRepository;
import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;
import br.com.entregas.Entregas.modules.orderItem.repositories.OrderItemRepository;
import br.com.entregas.Entregas.modules.product.dtos.mapper.ProductMapper;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.product.services.ProductService;
import br.com.entregas.Entregas.modules.productItem.dtos.mapper.ProductItemMapper;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;
import br.com.entregas.Entregas.modules.productItem.repositories.ProductItemRepository;
import br.com.entregas.Entregas.modules.productItem.services.ProductItemService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderItemService {
        private OrderItemRepository repository;
        private ProductItemRepository productItemRepository;
        private ProductItemService productItemService;
        private ProductItemMapper productItemMapper;
        private ProductRepository productRepository;
        private OrderRepository orderRepository;
        private ProductService productService;
        private ProductMapper productMapper;

        @Transactional
        public List<OrderItemModel> save(OrderItemModel orderItem) {
                List<OrderItemModel> listOrderItem = new ArrayList<>();
                List<String> institutesIds = new ArrayList<>();

                System.out.println(orderItem.getProductItens());
                
                // listando os ids de produtos e adicionando os seus ids de institutos em uma lista, caso já não exista
                for (ProductItemModel productItem : orderItem.getProductItens()) {
                        System.out.println(productItem);
                        
                        ProductItemModel newProductItem = productItemRepository.findById(productItem.getId()).get();
                        ProductModel newProduct = productRepository.findById(newProductItem.getProduct().getId()).get();
                        if(!institutesIds.contains(newProduct.getInstitute().getId())){
                                institutesIds.add(newProduct.getInstitute().getId());
                        }                
                }
                // Ao final temos uma lista de ids de institutes que contem produtos selecionados do item produto


                // Aqui lista todos os ids de institutes 
                for (String institutesId : institutesIds) {
                        List<ProductItemModel> productItemList = new ArrayList<>();
                        double total = 0;

                        // aqui faz a mesma coisa em cima só que verifica se o id do produto é igual ao que ta na lista de idsprodutos
                        for (ProductItemModel productItem : orderItem.getProductItens()) {
                                ProductItemModel newProductItem = productItemRepository.findById(productItem.getId()).get();
                                ProductModel newProduct = productRepository.findById(newProductItem.getProduct().getId()).get();
                                
                                if (productItemRepository.findById(productItem.getId()).get().getQuantity() > productRepository.findById(newProductItem.getProduct().getId()).get().getQuantity()) {
                                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                                }
                                // Diminuindo a quantidade e atualizando o produto
                                newProduct.setQuantity(productRepository.findById(newProductItem.getProduct().getId()).get().getQuantity() - productItemRepository.findById(productItem.getId()).get().getQuantity());
                                productService.update(productMapper.toDto(newProduct), productRepository.findById(newProductItem.getProduct().getId()).get().getId());
                                newProductItem.setActived(false);
                                productItemService.update(productItemMapper.toDto(newProductItem), productItemRepository.findById(newProductItem.getId()).get().getId());
                                if (newProduct.getInstitute().getId().equals(institutesId)) {
                                        productItemList.add(newProductItem);
                                        total += newProductItem.getPrice();
                                }
                        }

                        // no final é adicionado um produto em uma lista de itens pra agrupar de acordo com o institute
                        

                        // Aqui é criado um pedido para cada loja 
                        OrderModel newOrderModel = new OrderModel();
                        newOrderModel.setPrice(total);
                        newOrderModel.setStatus(StatusOrder.REQUESTED);
                        newOrderModel.setUpdated(LocalDateTime.now());
                        orderRepository.save(newOrderModel);


                        // Aqui é criado um novo item pedido e adicionado na lista de itens pedido 
                        OrderItemModel newOrderItemModel = new OrderItemModel();
                        newOrderItemModel.setOrder(newOrderModel);
                        newOrderItemModel.setProductItens(productItemList);
                        listOrderItem.add(newOrderItemModel);
                }

                return repository.saveAll(listOrderItem);

          
        }
}
