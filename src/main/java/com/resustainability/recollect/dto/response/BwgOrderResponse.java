package com.resustainability.recollect.dto.response;

import java.util.List;

public record BwgOrderResponse(
		IBwgOrderResponse details,
		List<ItemCategoryTypeResponse> types,
		List<IBwgOrderUsedBagResponse> usedBags,
		IInvoiceResponse invoice
) {}
