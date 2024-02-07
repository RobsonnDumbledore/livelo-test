package br.com.codart.domain.brand;

public class Brand {

    private final BrandID id;
    private final String value;
    private final boolean active;

    private Brand(BrandID id, String value, boolean active) {
        this.id = id;
        this.value = value;
        this.active = active;
    }

    public static Brand newBrand(
            String value, boolean active
    ) {
        BrandID id = BrandID.unique();
        return new Brand(id, value, active);
    }

    public static Brand with(
            BrandID id, String value, boolean active
    ) {
        return new Brand(id, value, active);
    }

    public BrandID getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public boolean isActive() {
        return active;
    }
}
