/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.serviceInterface;

import com.lantos.model.Book;
import com.lantos.model.Category;
import java.util.List;

/**
 *
 * @author PETI
 */
public interface BookService {
    
    public void saveBook(Book b);
    public List<Book> getAllBook();
    public Book getBookById(long id);
    public void removeBookById(long id);
    public void updateBook(Book e);
    public Category getCategoryByName(String category);
    public List<Category> getAllCategory();
    
}
