package pro.sky.recipeapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Recipe;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {

    private final FilesServiceImpl filesService;

    private long recipeIdGenerator = 1;
    private Map<Long, Recipe> recipes = new HashMap<>();

    public RecipeService(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    public Recipe addRecipe(Recipe recipe) {
        recipes.put(recipeIdGenerator++, recipe);
        saveToFile();
        return recipe;
    }

    public Optional<Recipe> getRecipeById(long recipeId) {
        return Optional.ofNullable(recipes.get(recipeId));
    }

    public Recipe editing(long recipeId, Recipe recipe) {
        if (recipes.containsKey(recipeId) && recipes.get(recipeId) != null) {
            Recipe result = recipes.replace(recipeId, recipe);
            saveToFile();
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public Optional<Recipe> delete(long recipeId) {
        return Optional.ofNullable(recipes.remove(recipeId));
    }

    public Map<Long, Recipe> getAll() {
        return new HashMap<>(recipes);
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveRecipesFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromRecipesFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
