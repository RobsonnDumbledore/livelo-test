package br.com.codart.domain.brand;

import java.util.Map;
import java.util.Set;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;

public interface BrandGateway {

    void createBrand(Set<Brand> brands);
    void changeBrandStatus(Map<BrandID, Boolean> brandStatus);
    void updateBrand(Brand brand);
    void deleteBrand(Set<BrandID> brandIds);
    Pagination<Brand> findAllBrands(String brandName, SearchQuery searchQuery);
    Brand findBrandById(BrandID brandId);

}
