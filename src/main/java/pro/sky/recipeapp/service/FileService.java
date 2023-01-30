package pro.sky.recipeapp.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public interface FileService {
    //    private Path pathOfIngredients = Path.of(dataFilePath,ingredientsFile);
    //    private Path pathOfRecipes = Path.of(dataFilePath, recipesFile);
    boolean saveIngredientsFile(String json);

    boolean saveRecipesFile(String json);
}
