package persistence;

import model.GroceryLists;

import java.io.File;

public interface ModelSerializer {
    GroceryLists deserialize(File sourceFile);

    void serialize(File targetFile, GroceryLists groceryLists);
}
