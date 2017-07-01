package kjkrol.eshop.ordering.integration

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.core.Relation
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@FeignClient(serviceId = "PRODUCT-CATALOG-SERVICE")
internal interface ProductCatalogClient {

    @RequestMapping(path = arrayOf("/product"), method = arrayOf(RequestMethod.GET))
    fun findAll(@RequestParam("ids") ids: List<UUID>): PagedResources<Resource<ProductResponse>>

}

@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "products")
internal data class ProductResponse @JsonCreator constructor(
        @JsonProperty("productId") val productId: UUID,
        @JsonProperty("name") val name: String,
        @JsonProperty("price") val price: Double
)