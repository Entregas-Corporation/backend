package br.com.entregas.Entregas.modules.orderItem.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.services.sendEmail.SendEmailService;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.models.OrderModel;
import br.com.entregas.Entregas.modules.order.repositories.OrderRepository;
import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;
import br.com.entregas.Entregas.modules.orderItem.repositories.OrderItemRepository;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.productItem.dtos.mapper.ProductItemMapper;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;
import br.com.entregas.Entregas.modules.productItem.repositories.ProductItemRepository;
import br.com.entregas.Entregas.modules.productItem.services.ProductItemService;
import br.com.entregas.Entregas.modules.user.repositories.UserRepository;
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
        private InstituteRepository instituteRepository;
        private SendEmailService sendEmailService;
        private UserRepository userRepository;

        @Transactional
        public List<OrderItemModel> save(OrderItemModel orderItem) {
                List<OrderItemModel> listOrderItem = new ArrayList<>();
                List<String> institutesIds = new ArrayList<>();

                for (ProductItemModel productItem : orderItem.getProductItens()) {
                        ProductItemModel newProductItem = productItemRepository.findById(productItem.getId()).get();
                        ProductModel newProduct = productRepository.findById(newProductItem.getProduct().getId()).get();
                        if (!institutesIds.contains(newProduct.getInstitute().getId())) {
                                institutesIds.add(newProduct.getInstitute().getId());
                        }
                }

                for (String institutesId : institutesIds) {
                        List<ProductItemModel> productItemList = new ArrayList<>();
                        double freight = 0;

                        for (ProductItemModel productItem : orderItem.getProductItens()) {
                                ProductItemModel newProductItem = productItemRepository.findById(productItem.getId())
                                                .get();
                                ProductModel newProduct = productRepository
                                                .findById(newProductItem.getProduct().getId()).get();

                                if (newProduct.getInstitute().getId().equals(institutesId)) {

                                        newProductItem.setActived(false);
                                        productItemService.updateByOrder(productItemMapper.toDto(newProductItem),
                                                        productItemRepository.findById(newProductItem.getId()).get()
                                                                        .getId());
                                        freight = instituteRepository.findById(institutesId).get().getFreight_cost_km();

                                        productItemList.add(newProductItem);
                                }
                        }

                        double freightCost = freight * orderItem.getDistance();

                        String userName = userRepository
                                        .findById(instituteRepository.findById(institutesId).get().getUser().getId())
                                        .get().getName();

                        String userEmail = userRepository
                                        .findById(instituteRepository.findById(institutesId).get().getUser().getId())
                                        .get().getEmail();

                        OrderModel newOrderModel = new OrderModel();
                        newOrderModel.setStatus(StatusOrder.REQUESTED);
                        newOrderModel.setFreight(freightCost);
                        newOrderModel.setInstitute(instituteRepository.findById(institutesId).get());
                        newOrderModel.setUserName(userName);
                        newOrderModel.setUserEmail(userEmail);
                        newOrderModel.setUpdated(LocalDateTime.now());
                        orderRepository.save(newOrderModel);
                        sendEmailService.sendRequestedOrder(
                                        newOrderModel.getUserName(), newOrderModel.getUserEmail(),
                                        instituteRepository.findById(institutesId).get().getName());

                        OrderItemModel newOrderItemModel = new OrderItemModel();
                        newOrderItemModel.setOrder(newOrderModel);
                        newOrderItemModel.setProductItens(productItemList);
                        newOrderItemModel.setInstitute(orderItem.getInstitute());
                        newOrderItemModel.setDistance(orderItem.getDistance());

                        for (ProductItemModel productItem : productItemList) {
                                productItem.setOrderItem(newOrderItemModel);

                                productItemService.update(productItemMapper.toDto(productItem), productItem.getId());
                        }

                        listOrderItem.add(newOrderItemModel);
                }

                return repository.saveAll(listOrderItem);

        }
}
