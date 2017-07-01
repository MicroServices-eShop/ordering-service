package kjkrol.eshop.ordering.order

import org.springframework.hateoas.core.Relation
import java.util.UUID

@Relation(collectionRelation = "orders")
internal data class OrderDto(val id: UUID, val totalPrice: Double, val customerId: UUID)