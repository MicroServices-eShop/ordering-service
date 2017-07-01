package kjkrol.eshop.ordering.order.create

import com.fasterxml.jackson.annotation.JsonCreator
import kjkrol.eshop.ordering.order.item.create.OrderItemRequest
import java.util.UUID

internal data class PlaceOrderRequest
@JsonCreator constructor(val customerId: UUID, val orderItems: List<OrderItemRequest>)