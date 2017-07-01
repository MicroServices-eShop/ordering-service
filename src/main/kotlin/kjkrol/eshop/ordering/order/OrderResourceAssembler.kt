package kjkrol.eshop.ordering.order

import kjkrol.eshop.ordering.order.item.OrderItemController
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider
import org.springframework.data.domain.PageRequest
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

@org.springframework.stereotype.Component
internal class OrderResourceAssembler(val entityLinks: EntityLinks,
                                      @Qualifier("customerRegisterServiceInstanceProvider")
                                      val customerRegisterServiceInstanceProvider: DynamicServiceInstanceProvider)
    : org.springframework.hateoas.ResourceAssembler<OrderDto, Resource<OrderDto>> {

    override fun toResource(order: OrderDto): Resource<OrderDto> {
        val selfLink: Link = linkTo(methodOn(OrderController::class.java).findById(order.id)).withSelfRel()
        val allOrdersLink = entityLinks.linkToCollectionResource(OrderDto::class.java)
                .withRel("all-orders")
        @Suppress("INTERFACE_STATIC_METHOD_CALL_FROM_JAVA6_TARGET")
        val productsLink: Link = linkTo(methodOn(OrderItemController::class.java).findOrderProducts(orderId = order.id,
                pageable = PageRequest(0, 10),
                pagedResourcesAssembler = null))
                .withRel("order-products")
        val customerLink: Link = Link("${customerRegisterServiceInstanceProvider.serviceInstance.uri}/customer/{id}")
                .expand(order.customerId)
                .withRel("customer")
        return Resource(order, selfLink, productsLink, allOrdersLink, customerLink)
    }
}