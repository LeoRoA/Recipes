package pro.sky.recipeapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngredientService {

    private long ingredientIdGenerator = 1;
    private Map<Long, Ingredient> ingredients = new HashMap<>();

    private final FilesServiceImpl filesService;

    public IngredientService(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }


    public Ingredient addIngredient(Ingredient ingredient) {
        ingredients.put(ingredientIdGenerator++, ingredient);
        saveToFile();
        return ingredient;
    }

    public Optional<Ingredient> getIngredientById(long ingredientId) {
        return Optional.ofNullable(ingredients.get(ingredientId));
    }

    public Ingredient editing(long ingredientId, Ingredient ingredient) {
        if (ingredients.containsKey(ingredientId) && ingredients.get(ingredientId) != null) {
            Ingredient result = ingredients.replace(ingredientId, ingredient);
            saveToFile();
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Optional<Ingredient> delete(long ingredientId) {
        return Optional.ofNullable(ingredients.remove(ingredientId));
    }

    public Map<Long, Ingredient> getAll() {
        return new HashMap<>(ingredients);
    }



    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveIngredientsFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromIngredientsFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
