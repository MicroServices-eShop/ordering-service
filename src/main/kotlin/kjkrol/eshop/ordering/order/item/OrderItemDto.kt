package kjkrol.eshop.ordering.order.item

import org.springframework.hateoas.core.Relation
import java.util.UUID

@Relation(collectionRelation = "order-items")
internal data class OrderItemDto(val orderId: UUID, val productId: UUID, val quantity: Int)