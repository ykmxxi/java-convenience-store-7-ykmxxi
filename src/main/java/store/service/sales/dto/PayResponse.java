package store.service.sales.dto;

import java.util.List;

public record PayResponse(
        List<BuyingProductResponse> buyingProducts,
        List<PromotionProductResponse> promotionProducts,
        TotalAmountResponse totalAmountResponse,
        long promotionAmount,
        long membershipAmount,
        long totalPayAmount
) {
}
