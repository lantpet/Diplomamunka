package com.lantos.manager;

import com.lantos.model.Book;
import com.lantos.model.Category;
import com.lantos.serviceInterface.BookService;
import java.io.IOException;

import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class BookManager {
    
    private BookService bookService;
    
    public boolean modifyBook(Map<String, String> reqPar,MultipartFile transferFile) throws IOException{
        String idstr = reqPar.get("hiddenBookId");
        Long id = null;
        byte[] byteImage = null;
        String clobImage = "";
        if(idstr!=null){
            id=  Long.parseLong(idstr);
        }
        Book book = null;
        if(id!=null){
            book= bookService.getBookById(id);
        }
        if(book!=null){
        Category category = bookService.getCategoryByName(reqPar.get("category"));
        book.setTitle(reqPar.get("title"));
        book.setCategory(category);
        book.setWriter(reqPar.get("writer"));
        book.setPrice(Integer.parseInt(reqPar.get("price")));
        book.setReleaseDate(Integer.parseInt(reqPar.get("release_date")));
        if(transferFile!=null && !transferFile.getOriginalFilename().equals("")){
            byteImage = IOUtils.toByteArray(transferFile.getInputStream());
            clobImage = new org.apache.commons.codec.binary.Base64().encodeAsString(byteImage);
            book.setImage(clobImage);
        }
        this.bookService.updateBook(book);
        
        }
        return true;
        
    }
    
    public void deleteBookById(long id){
        bookService.removeBookById(id);
    }
    
    public boolean addedBook(Map<String, String> reqPar, MultipartFile transferFile) throws IOException{

        String title = reqPar.get("title");
        String writer = reqPar.get("writer");
        String category = reqPar.get("category");
        String releaseDate = reqPar.get("releaseDate");
        String price = reqPar.get("price");
        byte[] byteImage = null;
        String clobImage = "";
        
        Category categorys = bookService.getCategoryByName(category);
        
        if(transferFile!=null){
            byteImage = IOUtils.toByteArray(transferFile.getInputStream());
            clobImage = new org.apache.commons.codec.binary.Base64().encodeAsString(byteImage);
        }
        
        Book book = new Book();
        book.setImage(clobImage);
        book.setCategory(categorys);
        book.setTitle(title);
        book.setWriter(writer);
        book.setReleaseDate(Integer.parseInt(releaseDate));
        book.setPrice(Integer.parseInt(price));
           
        this.bookService.saveBook(book);
        return true;
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    public String getBookDetailsAsSpecialString(long bookId) {
        String splitter = "__asplit__";
        Book book = this.bookService.getBookById(bookId);
        String title = "";
        String writer = "";
        String release_date = "";
        String category ="";
        String price = "";
        if(book.getCategory().getCategoryName()!=null) category = book.getCategory().getCategoryName();
        price = Integer.toString(book.getPrice());
        if (book.getTitle()!= null) {
            title = book.getTitle();
        }
        if (book.getWriter() != null) {
            writer = book.getWriter();
        }
        if(Integer.toString(book.getReleaseDate())!=null) release_date = Integer.toString(book.getReleaseDate());

        String result = title + splitter + 
                 writer+ splitter + 
                release_date + splitter +
                category+ splitter + 
                price;

        return result;
    }
}
