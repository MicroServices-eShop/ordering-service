package kjkrol.eshop.ordering.order.util

import kjkrol.eshop.ordering.order.Order
import kjkrol.eshop.ordering.order.OrderDto
import kjkrol.eshop.ordering.order.create.PlaceOrderRequest
import kjkrol.eshop.ordering.order.item.OrderItem
import kjkrol.eshop.ordering.order.item.OrderItemDto
import kjkrol.eshop.ordering.order.item.create.OrderItemRequest
import java.util.UUID

internal fun Order.toDto() = OrderDto(id = this.id, totalPrice = this.totalPrice, customerId = this.customerId)

internal fun PlaceOrderRequest.toEntity(totalPrice: Double): Order = Order(customerId = this.customerId, totalPrice = totalPrice)

internal fun OrderItemRequest.toEntity(orderId: UUID): OrderItem = OrderItem(orderId = orderId, productId = this.productId, quantity = this.quantity)

internal fun OrderItem.toDto() = OrderItemDto(orderId = this.orderId, productId = this.productId, quantity = this.quantity)
