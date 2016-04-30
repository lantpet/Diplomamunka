package com.lantos.manager;

import com.lantos.base64password.CreateAndValidatePassword;
import com.lantos.model.Book;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MailManager {

    private JavaMailSender mailSender;
    private UserService userservice;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public UserService getUserservice() {
        return userservice;
    }

    public void setUserservice(UserService userservice) {
        this.userservice = userservice;
    }

    public void sendRecoverPassword(String email) {
        String address = email;
        String subject = "Jelszó változtatás";
        String token = "";

        User user = null;
        if(address!=null){
         user=this.userservice.getUserByEmail(address);
        }
        if (user != null) {

            token = CreateAndValidatePassword.base64Encode("ysxdfcgvhbjnxdfcgvh");
            user.setPassword(token);
            this.userservice.updateUser(user);
            String text = "A jelenlegi jelszavad a következő: " + "ysxdfcgvhbjnxdfcgvh";
            sendMail(address, subject, text);
        }
    }
 
    public void sendEmailToUser(List<Book> lista, String email) {
        User user = null;
        if(email !=null){
         user =  this.userservice.getUserByEmail(email);
        }
        String address = user.getEmail();
        String subject = "webshop vásárlás";
        String text = "";
        int price = 0;
        for(Book list: lista){
            text +="<p>" +  list.getTitle() + " :" + list.getPrice() + "HUF" +"</p>";
            price += list.getPrice();
        }
        
        String formatted_text ="<h2>" + "Köszönjük vásárlását" + "</h2>"+text+"</br>" + "Végösszeg:" + price + "Ft" + "</br>"; 
        formatted_text += "<br><i> Send by: Lantos Péter Mailing System</i>";

        sendMail(address, subject, formatted_text);
    }

    private void sendMail(final String address, final String subject, final String text) {
        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(address);
                message.setSubject(subject);
                message.setText(text, true);
            }
        });
    }

}
