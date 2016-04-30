/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.controller;

import com.lantos.manager.BookManager;
import com.lantos.manager.MailManager;
import com.lantos.model.Book;
import com.lantos.serviceInterface.BookService;
import com.lantos.serviceInterface.UserService;
import com.lantos.sessionmanager.SessionManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"productList"})
public class CartController {

    BookManager bookManager = new BookManager();
    MailManager mailManager = new MailManager();

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setBookService(UserService es) {
        this.mailManager.setUserservice(es);
    }
    
    @ModelAttribute("productList")
    public List<Book> createShoppingCart() {
        return new ArrayList<Book>();
    }

    @Autowired
    public void setMailService(JavaMailSender mailSender) {
        this.mailManager.setMailSender(mailSender);
    }

    @Autowired(required = true)
    @Qualifier(value = "bookService")
    public void setBookService(BookService es) {
        this.bookManager.setBookService(es);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(Model model, @ModelAttribute("productList") List<Book> cart, HttpSession session) {
        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }
        
        int price = 0;
        for (Book books : cart) {
            price += books.getPrice();
        }
        model.addAttribute("price", price);
        return "cart";
    }

    @RequestMapping(value = "/addProduct.{bookId}")
    public String addProduct(@PathVariable("bookId") long id,
            @ModelAttribute("productList") List<Book> cart) {
        Book book = this.bookManager.getBookService().getBookById(id);
        cart.add(book);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/removeProduct.{bookId}")
    public String removeProductFromCart(@PathVariable("bookId") long id,
            @ModelAttribute("productList") List<Book> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == id) {
                cart.remove(i);
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping(value = "/buyProduct")
    public String payForCartItem(HttpSession session,RedirectAttributes redirAttr,
            @ModelAttribute("productList") List<Book> cart) {
        String email = (String) session.getAttribute("userEmail");
        this.mailManager.sendEmailToUser(cart, email);
        cart.clear();
        redirAttr.addFlashAttribute("succesPayed", "Sikeres vásárlás");
        return "redirect:/cart";
    }

}
