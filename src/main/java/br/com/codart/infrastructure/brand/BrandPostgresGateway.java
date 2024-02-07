package br.com.codart.infrastructure.brand;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import br.com.codart.domain.brand.Brand;
import br.com.codart.domain.brand.BrandID;
import org.springframework.data.domain.Sort;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;
import br.com.codart.domain.brand.BrandGateway;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.PageRequest;
import br.com.codart.domain.exceptions.NotFoundException;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;
import br.com.codart.infrastructure.brand.persistence.BrandEntity;
import static br.com.codart.domain.utils.CollectionUtils.isNotEmpty;
import br.com.codart.infrastructure.utils.GenericBatchUpdateService;
import br.com.codart.infrastructure.brand.persistence.BrandRepository;

@Component
public class BrandPostgresGateway implements BrandGateway {

    private final BrandRepository brandRepository;
    private final GenericBatchUpdateService genericBatchUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(BrandPostgresGateway.class);

    public BrandPostgresGateway(
            BrandRepository brandRepository,
            GenericBatchUpdateService genericBatchUpdateService
    ) {
        this.brandRepository = brandRepository;
        this.genericBatchUpdateService = genericBatchUpdateService;
    }

    @Override
    public void createBrand(Set<Brand> brands) {
        logger.info("Starting creation of brands. Number of brands to be created: {}", brands.size());

        final var brandsEntity = mapTo(brands, BrandEntity::fromDomain);

        this.brandRepository.saveAll(brandsEntity);
    }

    @Override
    public void changeBrandStatus(Map<BrandID, Boolean> brandStatus) {
        logger.info("Starting status change for {} brands.", brandStatus.size());

        Map<String, Boolean> convertedBrandStatus = brandStatus.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getValue(),
                        Map.Entry::getValue
                ));

        final var query = "UPDATE brand SET active = :isActive WHERE id = :brandId";
        genericBatchUpdateService.executeBatchUpdate(query, convertedBrandStatus, "brandId", BrandEntity.class);
    }

    @Override
    public void updateBrand(Brand brand) {
        logger.info("Updating brand with ID: {}", brand.getId());

        final var brandEntity = findBrandById(brand.getId());
        this.brandRepository.save(BrandEntity.fromDomain(brandEntity));
    }

    @Override
    public void deleteBrand(Set<BrandID> brandIds) {
        logger.info("Deleting brands for : {}", brandIds.size());

        if (isNotEmpty(brandIds)) {
            final var ids = mapTo(brandIds, BrandID::getValue);
            this.brandRepository.deleteByIdIn(ids);
        }
    }

    @Override
    public Pagination<Brand> findAllBrands(String brandName, SearchQuery searchQuery) {

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var pageResult = this.brandRepository.findAllCategories(brandName, page);

        return new Pagination<>(
                pageResult.map(BrandEntity::toDomain).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public Brand findBrandById(BrandID brandId) {
        logger.info("Searching for BrandEntity with ID: {}", brandId);

        final var brandEntity = this.brandRepository.findById(brandId.getValue())
                .orElseThrow(() -> new NotFoundException("BrandEntity not found for ID: " + brandId.getValue()));

        return BrandEntity.toDomain(brandEntity);
    }
}
