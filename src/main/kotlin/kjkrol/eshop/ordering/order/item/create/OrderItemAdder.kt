package kjkrol.eshop.ordering.order.item.create

import kjkrol.eshop.ordering.order.item.OrderItemRepository
import kjkrol.eshop.ordering.order.util.toDto
import kjkrol.eshop.ordering.order.util.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
internal open class OrderItemAdder(val orderItemRepository: OrderItemRepository) {

    @Transactional
    fun saveOrderItems(orderItems: List<OrderItemRequest>, orderId: UUID) = orderItems
            .map { it.toEntity(orderId) }
            .map { orderItemRepository.save(it) }
            .map { it.toDto() }
}