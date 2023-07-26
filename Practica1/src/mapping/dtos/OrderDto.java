package mapping.dtos;

import domain.enums.OrderType;
import domain.models.Client;
import domain.models.Product;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(Long id,
                       OrderType status,
                       LocalDate orderDate,
                       LocalDate deliveryDate,
                       List<Product> products,
                       Client client){
}
