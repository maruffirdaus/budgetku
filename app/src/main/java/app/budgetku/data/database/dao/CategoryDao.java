package app.budgetku.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.budgetku.data.database.entity.Category;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Query("SELECT * FROM category")
    List<Category> getCategories();

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}