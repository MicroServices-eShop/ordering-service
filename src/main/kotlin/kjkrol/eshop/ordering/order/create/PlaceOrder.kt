package kjkrol.eshop.ordering.order.create

import kjkrol.eshop.ordering.integration.ProductCatalogClient
import kjkrol.eshop.ordering.integration.ProductResponse
import kjkrol.eshop.ordering.order.Order
import kjkrol.eshop.ordering.order.OrderDto
import kjkrol.eshop.ordering.order.OrderRepository
import kjkrol.eshop.ordering.order.item.create.OrderItemAdder
import kjkrol.eshop.ordering.order.item.create.OrderItemRequest
import kjkrol.eshop.ordering.order.util.toDto
import kjkrol.eshop.ordering.order.util.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.EntityNotFoundException


@Service
internal open class PlaceOrder(val orderRepository: OrderRepository, val orderItemAdder: OrderItemAdder,
                               val productCatalogClient: ProductCatalogClient) {

    @Transactional
    fun placeOrder(placeOrderRequest: PlaceOrderRequest): OrderDto {
        val productsById: Map<UUID, ProductResponse> = findProductsFromProductCatalog(placeOrderRequest.orderItems)
        val validatedOrders: Map<Boolean, List<OrderItemRequest>> = validateProducts(placeOrderRequest.orderItems, productsById)

        val validOrders = validatedOrders[true]
        val invalidOrders = validatedOrders[false]
        println("invalidOrders=$invalidOrders")
        when (validOrders) {
            null -> throw EntityNotFoundException()
            else -> {
                val totalPrice: Double = calculateTotalPrice(validOrders, productsById)
                val order: Order = orderRepository.save(placeOrderRequest.toEntity(totalPrice))
                orderItemAdder.saveOrderItems(orderItems = validOrders, orderId = order.id)
                return order.toDto()
            }
        }
    }

    // TODO: should iterate all pages or return non pageable collection
    private fun findProductsFromProductCatalog(orderItems: List<OrderItemRequest>)
            : Map<UUID, ProductResponse> = productCatalogClient.findAll(orderItems.map { it.productId })
            .content
            .map { it.content.productId to it.content }
            .toMap()

    private fun validateProducts(orderItems: List<OrderItemRequest>, products: Map<UUID, ProductResponse>)
            : Map<Boolean, List<OrderItemRequest>> = orderItems.groupBy { products.containsKey(it.productId) }

    private fun calculateTotalPrice(orderItems: List<OrderItemRequest>, products: Map<UUID, ProductResponse>): Double =
            0.0 + orderItems.map { (productId, quantity) -> quantity * (products[productId]?.price ?: 0.0) }.sum()

}
