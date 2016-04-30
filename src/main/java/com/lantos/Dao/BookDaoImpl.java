package com.lantos.Dao;

import com.lantos.daoInterface.BookDao;
import com.lantos.model.Book;
import com.lantos.model.Category;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BookDaoImpl implements BookDao {

    private SessionFactory sessionFactory;

    @Override
    public void saveBook(Book b) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(b);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Book> getAllBook() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery("from Book").list();
        return bookList;
    }

    @Override
    public Book getBookById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Book) session.get(Book.class, id);
    }

    @Override
    public void removeBookbById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book b = (Book) session.get(Book.class, new Long(id));
        if (b != null) {
            session.delete(b);
        }
    }

    @Override
    public void updateBook(Book e) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Category getCategoryByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Category where categoryName = :name");
        query.setParameter("name", name);
        List<Category> categoryList = query.list();
        return categoryList.size() < 1 ? null : categoryList.get(0);
    }

    @Override
    public List<Category> getAllCategory() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Category> categoryList = session.createQuery("from Category").list();
        return categoryList;
    }

}
