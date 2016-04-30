
package com.lantos.daoInterface;

import com.lantos.model.Book;
import com.lantos.model.Category;
import java.util.List;

public interface BookDao {
    
    public void saveBook(Book b);
    public List<Book> getAllBook();
    public Book getBookById(long id);
    public void removeBookbById(long id);
    public void updateBook(Book e);
    public Category getCategoryByName(String name);
    public List<Category> getAllCategory();
    
}
