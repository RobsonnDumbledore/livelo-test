package br.com.codart.infrastructure.category.persistence;

import java.util.List;
import java.util.Set;

import br.com.codart.infrastructure.product.persistence.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    @Modifying
    @Query("DELETE FROM CategoryEntity c WHERE c.id IN (:categoryIds)")
    void deleteByIdIn(@Param("categoryIds") Set<String> categoryIds);

    @Query("""
        SELECT c FROM CategoryEntity c
            WHERE c.active = true
                AND (:categoryName IS NULL OR :categoryName = '' OR c.name LIKE :categoryName%)
        """
    )
    Page<CategoryEntity> findAllCategories(
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity p SET p.active = :isActive WHERE p.id IN :categoryIds")
    void updateCategoryStatus(
            @Param("isActive") boolean isActive,
            @Param("categoryIds") List<String> categoryIds
    );

    @Query("SELECT p FROM CategoryEntity p WHERE p.id IN :categoryIds")
    List<CategoryEntity> findAllByIdIn(@Param("categoryIds") List<String> categoryIds);

}
