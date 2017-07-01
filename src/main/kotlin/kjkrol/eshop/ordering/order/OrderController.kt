package kjkrol.eshop.ordering.order

import kjkrol.eshop.ordering.order.create.PlaceOrder
import kjkrol.eshop.ordering.order.create.PlaceOrderRequest
import org.springframework.beans.support.PagedListHolder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.data.web.SortDefault
import org.springframework.data.web.SortDefault.SortDefaults
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = arrayOf("/order"), produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
@ExposesResourceFor(OrderDto::class)
internal class OrderController(val placeOrder: PlaceOrder, val orderQuery: OrderQuery,
                               val orderResourceAssembler: OrderResourceAssembler) {

    @PostMapping
    fun placeOrder(@RequestBody request: PlaceOrderRequest): ResponseEntity<Resource<OrderDto>> {
        val order: OrderDto = placeOrder.placeOrder(request)
        val resource: Resource<OrderDto> = orderResourceAssembler.toResource(order)
        return ResponseEntity(resource, HttpStatus.OK)
    }

    @GetMapping
    fun findAll(
            @PageableDefault(size = PagedListHolder.DEFAULT_PAGE_SIZE)
            @SortDefaults(SortDefault(sort = arrayOf("totalPrice"), direction = Sort.Direction.DESC))
            pageable: Pageable,
            pagedResourcesAssembler: PagedResourcesAssembler<OrderDto>): ResponseEntity<PagedResources<Resource<OrderDto>>> {
        val page: Page<OrderDto> = orderQuery.findAll(pageable)
        when {
            page.none() -> return ResponseEntity(HttpStatus.NOT_FOUND)
            else -> return ResponseEntity(pagedResourcesAssembler.toResource(page, orderResourceAssembler), HttpStatus.OK)
        }
    }

    @GetMapping(value = "{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Resource<OrderDto>> {
        val order: OrderDto = orderQuery.findById(id)
        val resource: Resource<OrderDto> = orderResourceAssembler.toResource(order)
        return ResponseEntity(resource, HttpStatus.OK)
    }

    @GetMapping(value = "customer/{id}")
    fun findCustomerOrders(
            @PathVariable("id") customerId: UUID,
            @SortDefaults(SortDefault(sort = arrayOf("totalPrice"), direction = Sort.Direction.DESC))
            pageable: Pageable,
            pagedResourcesAssembler: PagedResourcesAssembler<OrderDto>?): ResponseEntity<PagedResources<Resource<OrderDto>>?> {
        val page: Page<OrderDto> = orderQuery.findCustomerOrders(customerId, pageable)
        return ResponseEntity(pagedResourcesAssembler?.toResource(page, orderResourceAssembler), HttpStatus.OK)
    }

}