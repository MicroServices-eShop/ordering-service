package kjkrol.eshop.ordering.order.item

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = arrayOf("/order-item/"), produces = arrayOf(HAL_JSON_VALUE))
@ExposesResourceFor(OrderItemDto::class)
internal class OrderItemController(val orderItemQuery: OrderItemQuery, val orderItemResourceAssembler: OrderItemResourceAssembler) {

    @GetMapping(value = "{order-id}/product")
    fun findOrderProducts(@PathVariable("order-id") orderId: UUID,
                          pageable: Pageable,
                          pagedResourcesAssembler: PagedResourcesAssembler<OrderItemDto>?): ResponseEntity<PagedResources<Resource<OrderItemDto>>?> {
        val page: Page<OrderItemDto> = orderItemQuery.findOrderProducts(orderId, pageable)
        return ResponseEntity(pagedResourcesAssembler?.toResource(page, orderItemResourceAssembler), HttpStatus.OK)
    }

    @GetMapping(value = "{order-id}/product/{product-id}")
    fun findOrderItem(@PathVariable("order-id") orderId: UUID,
                      @PathVariable("product-id") productId: UUID): ResponseEntity<Resource<OrderItemDto>> {
        val orderItem: OrderItemDto = orderItemQuery.findOrderItemById(orderId = orderId, productId = productId)
        val resource: Resource<OrderItemDto> = orderItemResourceAssembler.toResource(orderItem)
        return ResponseEntity(resource, HttpStatus.OK)
    }
}