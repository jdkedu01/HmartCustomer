package hmart.customer.controller;

//Main Stream이 아님. 이 파일 불필요. staic
//이 파일이 없으면 hello.html, hello-template.html도 불필요
//정적컨텐츠, Web MVC Template, API를 설명하기 위한 구문

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebViewExampleController {
    @GetMapping("hello") //localhost:8080/hello때 아래 메서드 호출
    public String hello(Model model) {
        model.addAttribute("data", "전달 by data -- hello!!!");
        return "hello";  //templates/hello.html호출
    }
    @GetMapping("hello-param")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        // localhost:8080/hello-param?name=John
        // @RequestParam -- HTTP 요청의 쿼리 파라미터를 메서드의 매개변수로 바인딩
        // 즉 name이라는 변수에 'John'이 할당됨
        // model.addAttribute('name', name) : 앞 name은 아래 html에서 사용될 변수 이름
        return "helloTemplate";
    }
    @GetMapping("hello-string")
    @ResponseBody  // model로 전달되지 않고 return 구문 값이 바로 body로만 구성됨
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
        //ResponseBody 때문에 후처리HTML없이 내용만 보내짐. 소스보기 하면 알 수 있음
    }
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // Body만 있는 파일. 클래스 정보가 JSON type으로 전달됨
    }
    static class Hello {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}