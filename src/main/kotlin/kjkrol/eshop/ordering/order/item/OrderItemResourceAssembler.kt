package kjkrol.eshop.ordering.order.item

import kjkrol.eshop.ordering.order.OrderController
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceAssembler
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
internal class OrderItemResourceAssembler(@Qualifier("productCatalogServiceInstanceProvider")
                                          val productCatalogServiceInstanceProvider: DynamicServiceInstanceProvider)
    : ResourceAssembler<OrderItemDto, Resource<OrderItemDto>> {

    override fun toResource(orderItem: OrderItemDto): Resource<OrderItemDto> {
        val selfLink: Link = linkTo(methodOn(OrderItemController::class.java).findOrderItem(
                orderId = orderItem.orderId,
                productId = orderItem.productId))
                .withSelfRel()
        val orderLink = linkTo(methodOn(OrderController::class.java).findById(orderItem.orderId))
                .withRel("order")
        val productLink: Link = Link("${productCatalogServiceInstanceProvider.serviceInstance.uri}/product/{id}")
                .expand(orderItem.productId)
                .withRel("product")
        return Resource(orderItem, selfLink, orderLink, productLink)
    }

}