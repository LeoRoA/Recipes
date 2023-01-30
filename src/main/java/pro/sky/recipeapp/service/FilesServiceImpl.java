package pro.sky.recipeapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service

public class FilesServiceImpl implements FileService {
    @Value("${path.to.ingredients.file}")
    private String dataFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFile;

    @Value("${name.of.recipes.file}")
    private String recipesFile;


@Override
    public boolean saveIngredientsFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath,ingredientsFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
@Override
    public boolean saveRecipesFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath,recipesFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFromIngredientsFile() {
        try {
            return Files.readString(Path.of(dataFilePath,ingredientsFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFromRecipesFile() {
        try {
            return Files.readString(Path.of(dataFilePath,recipesFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

