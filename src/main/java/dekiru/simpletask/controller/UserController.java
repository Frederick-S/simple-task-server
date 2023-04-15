package dekiru.simpletask.controller;

import dekiru.simpletask.Constant;
import dekiru.simpletask.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The api routes for users.
 */
@RestController
public class UserController extends BaseController {
    @GetMapping("/users/me")
    public UserDto getMe(HttpSession session) {
        return (UserDto) session.getAttribute(Constant.ME);
    }
}
