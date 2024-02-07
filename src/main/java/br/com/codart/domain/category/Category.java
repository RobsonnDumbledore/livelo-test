package br.com.codart.domain.category;

public class Category {

    private final CategoryID id;
    private final String name;
    private boolean active;

    private Category(CategoryID id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public static Category newCategory(String name) {
        CategoryID id = CategoryID.unique();
        return new Category(id, name, true);
    }

    public static Category with(CategoryID id, String name, boolean active) {
        return new Category(id, name, active);
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
