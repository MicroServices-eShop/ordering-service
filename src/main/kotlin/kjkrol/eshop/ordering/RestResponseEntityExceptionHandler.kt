package kjkrol.eshop.ordering

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.NoSuchElementException
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: Exception, request: WebRequest): ResponseEntity<String> =
            ResponseEntity("Access denied message here", HttpHeaders(), HttpStatus.FORBIDDEN)

    @ExceptionHandler(EntityNotFoundException::class, NoSuchElementException::class)
    fun handleNoSuchElementException(ex: Exception, request: WebRequest): ResponseEntity<String?> =
            ResponseEntity(ex.message, HttpHeaders(), HttpStatus.NOT_FOUND)
}