package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.PlaceOrderRequest;
import com.resustainability.recollect.dto.request.UpdateOrderScheduleDateRequest;
import com.resustainability.recollect.dto.request.AddOrderRequest;
import com.resustainability.recollect.dto.request.CancelOrderRequest;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.service.OrderService;
import com.resustainability.recollect.tag.OrderType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/order")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'PROVIDER')")
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

    @GetMapping("/list-status")
    public APIResponse<List<EnumOrdinalResponse>> listStatusesInOrdinals() {
        return new APIResponse<>(
                orderService.listStatusesInOrdinals()
        );
    }

    @GetMapping("/list-cart/{orderId}")
    public APIResponse<List<IOrderCartItemResponse>> listAllCartItems(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        return new APIResponse<>(
                orderService.listAllCartItems(orderId)
        );
    }

    @GetMapping("/list-history")
    public APIResponse<Pager<IOrderHistoryResponse>> listScrapHistory(
            @RequestParam(value = "status", required = false) Set<String> status,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listHistory(status, null, searchCriteria)
        );
    }
    
    
    @PostMapping("/add-scraop-order")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Long> addScrap(@RequestBody AddOrderRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                orderService.add(request, OrderType.SCRAP)
        );
    }

    
    @PostMapping("/add-bio-order")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Long> addBioWaste(@RequestBody AddOrderRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                orderService.add(request, OrderType.BIO_WASTE)
        );
    }
    @GetMapping("/list-history-scrap")
    public APIResponse<Pager<IOrderHistoryResponse>> listHistoryScrap(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listHistory(null, OrderType.SCRAP, searchCriteria)
        );
    }

    
    @GetMapping("/list-history-bio")
    public APIResponse<Pager<IOrderHistoryResponse>> listHistoryBio(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listHistory(null, OrderType.BIO_WASTE, searchCriteria)
        );
    }

    @GetMapping("/list-assignable")
    public APIResponse<Pager<IOrderHistoryResponse>> listAssignable(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listAssignable(searchCriteria)
        );
    }

    @GetMapping("/list-timeline/{orderId}")
    public APIResponse<Pager<IOrderLogResponse>> listTimeline(
            @PathVariable(value = "orderId", required = false) Long orderId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                orderService.listTimeline(orderId, searchCriteria)
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
    
    @PutMapping("/reschedule")
    public APIResponse<Void> updateScheduledDate(
            @RequestBody UpdateOrderScheduleDateRequest request
    ) {
        orderService.updateScheduledDate(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_ORDER_SCHEDULE_DATE);
    }


    @GetMapping("/invoice/{orderId}")
    public APIResponse<InvoiceResponse> getInvoiceById(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        return new APIResponse<>(
                orderService.getInvoiceById(orderId)
        );
    }

    @PostMapping("/self-assign/{orderId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public APIResponse<IOrderHistoryResponse> selfAssign(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_SELF_ASSIGNED,
                orderService.getById(
                        orderService.selfAssign(orderId)
                )
        );
    }

    @PostMapping("/self-head-address/{orderId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public APIResponse<Void> logEventHeadedToAddress(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        orderService.logEventHeadedToAddress(orderId);
        return new APIResponse<>(Default.SUCCESS_HEADED_TO_ORDER_LOCATION);
    }

    @PostMapping("/self-reach-address/{orderId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public APIResponse<Void> logEventReachedToAddress(
            @PathVariable(value = "orderId", required = false) Long orderId
    ) {
        orderService.logEventReachedToAddress(orderId);
        return new APIResponse<>(Default.SUCCESS_REACHED_TO_ORDER_LOCATION);
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
