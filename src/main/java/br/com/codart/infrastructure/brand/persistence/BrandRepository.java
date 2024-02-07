package br.com.codart.infrastructure.brand.persistence;

import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, String> {

    @Modifying
    @Query("DELETE FROM BrandEntity c WHERE c.id IN (:brandIds)")
    void deleteByIdIn(@Param("brandIds") Set<String> brandIds);

    @Query("""
        SELECT c FROM CategoryEntity c
            WHERE c.active = true
                AND (:categoryName IS NULL OR :categoryName = '' OR c.name LIKE :categoryName%)
        """
    )
    Page<BrandEntity> findAllCategories(
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

}
