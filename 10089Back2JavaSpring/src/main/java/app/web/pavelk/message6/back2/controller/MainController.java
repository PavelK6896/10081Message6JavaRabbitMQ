package app.web.pavelk.message6.back2.controller;

import app.web.pavelk.message6.back2.setting.StompService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String returnNoFavicon() {
        return HtmlUtils.htmlEscape("index.html");
    }

}
