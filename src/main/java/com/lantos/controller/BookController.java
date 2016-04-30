/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.controller;

import com.lantos.manager.BookManager;
import com.lantos.manager.UserManager;
import com.lantos.model.Book;
import com.lantos.model.Category;
import com.lantos.model.Role;
import com.lantos.model.User;
import com.lantos.serviceInterface.BookService;
import com.lantos.serviceInterface.UserService;
import com.lantos.sessionmanager.SessionManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PETI
 */
@Controller
public class BookController {

    BookManager bookManager = new BookManager();
    UserManager usermanager = new UserManager();
    
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setBookService(UserService es) {
        this.usermanager.setUserService(es);
    }

    @Autowired(required = true)
    @Qualifier(value = "bookService")
    public void setBookService(BookService es) {
        this.bookManager.setBookService(es);
    }

    @RequestMapping(value = "/books")
    public String bookTable(HttpSession session, Model model) {
        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }
        
        List<Category> allCategory = this.bookManager.getBookService().getAllCategory();
        Map<String, String> categoryMap = new HashMap<String, String>();
        for (Category category : allCategory) {
            if (!category.getCategoryName().equals(categoryMap.values())) {
                categoryMap.put(category.getCategoryName(), category.getCategoryName());
            }
        }
        model.addAttribute("categoryMap", categoryMap);
        
        List<Book> books = this.bookManager.getBookService().getAllBook();
        model.addAttribute("bookList", books);
        model.addAttribute("roles",session.getAttribute("role"));
        return "books";
    }

    @RequestMapping(value = "/new_book")
    public String newbookForm(HttpSession session, Locale locale, Model model, @RequestParam Map<String, String> reqPar) {
        User user = this.usermanager.getUserService().getUserByEmail((String) session.getAttribute("userEmail"));
        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }
        
        if(user.getRole()!= Role.ADMIN){
            return "redirect:/books";
        }
        
        List<Category> allCategory = this.bookManager.getBookService().getAllCategory();
        Map<String, String> categoryMap = new HashMap<String, String>();
        for (Category category : allCategory) {
            if (!category.getCategoryName().equals(categoryMap.values())) {
                categoryMap.put(category.getCategoryName(), category.getCategoryName());
            }
        }
        model.addAttribute("categoryMap", categoryMap);

        return "new_book";
    }
    
    @RequestMapping(value="/removeBook.{bookId}")
    public String removeBook(HttpSession session,@PathVariable("bookId") long id){
        this.bookManager.deleteBookById(id);
        return "redirect:/books";
    }

    @RequestMapping(value = "/getImage.{bookId}", method = RequestMethod.GET)
    public void getImage(HttpSession session,@PathVariable("bookId") long id, HttpServletResponse response) throws IOException{
        Book book = this.bookManager.getBookService().getBookById(id);
        String s = book.getImage();
        byte[] newbyte = new org.apache.commons.codec.binary.Base64().decode(s); 
        response.setContentType("image/jpeg");
        response.getOutputStream().write(newbyte);
    }
    
    @RequestMapping(value = "/added_book", method = RequestMethod.POST)
    public String addedBook(HttpSession session, @RequestParam Map<String, String> reqPar,@RequestParam("transfer_file") MultipartFile transferFile) throws ParseException, IOException {

        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }

        if (this.bookManager.addedBook(reqPar, transferFile)) {
            return "redirect:/books";
        } else {
            return "redirect:/new_books";
        }
    }
    
     @RequestMapping(value = "/modify_book.{bookID}", method = RequestMethod.GET)
    public @ResponseBody
    String getBooksDetailsToModify(@PathVariable("bookID") long bookID) {
        return this.bookManager.getBookDetailsAsSpecialString(bookID);
    }
    
    @RequestMapping(value = "/modified_book", method = RequestMethod.POST)
    public String modifiedEmployee(HttpSession session, @RequestParam Map<String, String> reqPar, @RequestParam("transfer_file") MultipartFile transferFile) throws ParseException, IOException {

        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }

        this.bookManager.modifyBook(reqPar, transferFile);
        return "redirect:/books";
    }
}
