package kjkrol.eshop.ordering.order.item.create

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.UUID

internal data class OrderItemRequest @JsonCreator constructor(val productId: UUID, val quantity: Int)