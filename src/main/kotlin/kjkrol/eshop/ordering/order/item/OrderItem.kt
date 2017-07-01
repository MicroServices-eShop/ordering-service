package kjkrol.eshop.ordering.order.item

import org.hibernate.annotations.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.util.UUID
import java.util.UUID.randomUUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@Entity
@Table(name = "orders_products")
@IdClass(OrderItemId::class)
data class OrderItem(

        @Id
        @Column(name = "order_id", nullable = false)
        @Type(type = "uuid-char")
        val orderId: UUID,

        @Id
        @Column(name = "product_id", nullable = false)
        @Type(type = "uuid-char")
        val productId: UUID,

        @Column(name = "quantity", nullable = false)
        val quantity: Int

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
//        val product: Product
)

class OrderItemId(val orderId: UUID, val productId: UUID) : Serializable {
    constructor() : this(orderId = randomUUID(), productId = randomUUID())
}

interface OrderItemRepository : JpaRepository<OrderItem, OrderItemId> {
    fun findByOrderId(orderId: UUID, pageable: Pageable): Page<OrderItem>
}

