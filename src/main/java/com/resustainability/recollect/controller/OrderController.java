package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.PlaceOrderRequest;
import com.resustainability.recollect.dto.request.CancelOrderRequest;
import com.resustainability.recollect.dto.response.IOrderHistoryResponse;
import com.resustainability.recollect.dto.response.IOrderCancelReasonResponse;
import com.resustainability.recollect.service.OrderService;

import com.resustainability.recollect.tag.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recollect/v1/order")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
public class OrderController {
    private final OrderService orderService;

	@Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list-cancellation-reasons")
    public APIResponse<List<IOrderCancelReasonResponse>> listCancellationReasons() {
        return new APIResponse<>(
                orderService.listCancellationReasons()
        );
    }

    @GetMapping("/list-history")
    public APIResponse<Pager<IOrderHistoryResponse>> listHistory(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listHistory(searchCriteria)
        );
    }

    @GetMapping("/details/{orderId}")
    public APIResponse<IOrderHistoryResponse> getById(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        return new APIResponse<>(
                orderService.getById(orderId)
        );
    }

    @PostMapping("/place-scrap-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public APIResponse<IOrderHistoryResponse> placeScrapOrder(
            @RequestBody(required = false) PlaceOrderRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                orderService.getById(
                        orderService.placeOrder(request, OrderType.SCRAP)
                )
        );
    }

    @PostMapping("/place-bio-waste-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public APIResponse<IOrderHistoryResponse> placeBioWasteOrder(
            @RequestBody(required = false) PlaceOrderRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                orderService.getById(
                        orderService.placeOrder(request, OrderType.BIO_WASTE)
                )
        );
    }

    @PatchMapping("/cancel")
    public APIResponse<Void> update(
            @RequestBody(required = false) CancelOrderRequest request
    ) {
        orderService.cancelById(request);
        return new APIResponse<>(Default.SUCCESS_CANCEL_ORDER);
    }

    @DeleteMapping("/delete/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        orderService.deleteById(orderId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_ORDER);
    }
    
    @DeleteMapping("/un-delete/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> undeleteById(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        orderService.deleteById(orderId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_ORDER);
    }
}
