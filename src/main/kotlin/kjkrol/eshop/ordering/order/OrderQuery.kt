package kjkrol.eshop.ordering.order

import kjkrol.eshop.ordering.order.util.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
internal open class OrderQuery(val orderRepository: OrderRepository) {

    @Transactional(readOnly = true)
    fun findById(id: UUID): OrderDto = orderRepository
            .findById(id)
            .orElseThrow { NoSuchElementException() }
            .toDto()

    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable): Page<OrderDto> = orderRepository
            .findAll(pageable)
            .map { it.toDto() }

    @Transactional(readOnly = true)
    fun findCustomerOrders(customerId: UUID, pageable: Pageable): Page<OrderDto> = orderRepository
            .findByCustomerId(customerId, pageable)
            .map { it.toDto() }

}