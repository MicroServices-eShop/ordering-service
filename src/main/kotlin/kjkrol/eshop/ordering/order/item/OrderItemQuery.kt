package kjkrol.eshop.ordering.order.item

import kjkrol.eshop.ordering.order.util.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional.ofNullable
import java.util.UUID

@Service
internal open class OrderItemQuery(val orderItemRepository: OrderItemRepository) {

    @Transactional(readOnly = true)
    fun findOrderProducts(orderId: UUID, pageable: Pageable): Page<OrderItemDto> = orderItemRepository
            .findByOrderId(orderId, pageable)
            .map { it.toDto() }

    @Transactional(readOnly = true)
    fun findOrderItemById(orderId: UUID, productId: UUID): OrderItemDto = ofNullable(orderItemRepository
            .findOne(OrderItemId(orderId, productId)))
            .orElseThrow { NoSuchElementException() }
            .toDto()

}
