package com.askin.userauthservice.controller;

import com.askin.userauthservice.model.User;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ui")
public class UserUIController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/users/register");

            String json = "{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/ui/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/users/login");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", user.getUsername()));
            params.add(new BasicNameValuePair("password", user.getPassword()));

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/dashboard";
    }
}
