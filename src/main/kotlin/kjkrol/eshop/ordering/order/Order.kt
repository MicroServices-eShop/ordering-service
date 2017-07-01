package kjkrol.eshop.ordering.order

import kjkrol.eshop.ordering.order.item.OrderItem
import org.hibernate.annotations.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID
import javax.persistence.CascadeType.REMOVE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class Order(
        @Id
        @Type(type = "uuid-char")
        val id: UUID = UUID.randomUUID(),

        @Column(name = "customer_id", nullable = false)
        @Type(type = "uuid-char")
        val customerId: UUID,

        @Column(name = "totalPrice", precision = 10, scale = 2, nullable = false)
        val totalPrice: Double = 0.0,

        @OneToMany(mappedBy = "orderId", cascade = arrayOf(REMOVE), fetch = LAZY)
        val orderItems: Set<OrderItem> = emptySet())

interface OrderRepository : JpaRepository<Order, UUID> {
    fun findByCustomerId(customerId: UUID, pageable: Pageable): Page<Order>
    fun findById(id: UUID): Optional<Order>
}