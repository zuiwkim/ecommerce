package kr.hhplus.be.server.app.application.item;

public record GetItemsResult(
    String itemName,
    int itemPrice,
    int itemQuantity
) {
}
