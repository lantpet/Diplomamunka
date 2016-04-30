/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.service;

import com.lantos.daoInterface.BookDao;
import com.lantos.model.Book;
import com.lantos.model.Category;
import com.lantos.serviceInterface.BookService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PETI
 */
public class BookServiceImpl implements BookService{
    
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    
    @Override
    @Transactional
    public void saveBook(Book b) {
        this.bookDao.saveBook(b);        
    }


    @Override
    @Transactional
    public List<Book> getAllBook() {
       return this.bookDao.getAllBook();
    }

    @Override
    @Transactional
    public Book getBookById(long id) {
       return this.bookDao.getBookById(id);
    }

    @Override
    @Transactional
    public void removeBookById(long id) {
        this.bookDao.removeBookbById(id);
    }
    
    @Override
    @Transactional
    public void updateBook(Book e) {
        this.bookDao.updateBook(e);
    }

    @Override
    @Transactional
    public Category getCategoryByName(String category) {
        return this.bookDao.getCategoryByName(category);
    }

    @Override
    @Transactional
    public List<Category> getAllCategory() {
        return this.bookDao.getAllCategory();
        }
    
}
