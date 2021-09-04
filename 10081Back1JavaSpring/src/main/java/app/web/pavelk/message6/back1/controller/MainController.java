package app.web.pavelk.message6.back1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${server.port:0}")
    private String port;

    @GetMapping("/")
    public String returnNoFavicon() {
        return HtmlUtils.htmlEscape("index.html");
    }

    @GetMapping("/port")
    @ResponseBody
    public String getPort() {
        return port;
    }

}
