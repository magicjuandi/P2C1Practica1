package mapping.dtos;

import domain.enums.ProductType;

public record ProductDto(Long id,
                         String name,
                         ProductType category,
                         Double price) {
}
