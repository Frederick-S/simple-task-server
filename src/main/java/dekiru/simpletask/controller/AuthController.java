package dekiru.simpletask.controller;

import dekiru.simpletask.Constant;
import dekiru.simpletask.annotation.LoginRequired;
import dekiru.simpletask.dto.Response;
import dekiru.simpletask.dto.ResponseError;
import dekiru.simpletask.dto.UserDto;
import dekiru.simpletask.dto.UserLoginRequest;
import dekiru.simpletask.entity.User;
import dekiru.simpletask.repository.UserRepository;
import dekiru.simpletask.service.PasswordService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The api routes for auth.
 */
@RestController
public class AuthController extends BaseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisIndexedSessionRepository sessionRepository;

    @Autowired
    private PasswordService passwordService;

    /**
     * Login.
     *
     * @param userLoginRequest The request entity to login
     * @param session          Current http session
     * @return Current user
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Response<UserDto>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        User user = userRepository.findByName(userLoginRequest.getName());

        if (user == null) {
            return new ResponseEntity<>(
                    new Response<>(new ResponseError("User doesn't exist")), HttpStatus.NOT_FOUND);
        }

        if (!passwordService.matches(
                userLoginRequest.getPassword(), user.getEncryptedPassword())) {
            return new ResponseEntity<>(
                    new Response<>(new ResponseError("Invalid user name or password")),
                    HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());

        session.setAttribute(Constant.ME, userDto);

        return new ResponseEntity<>(new Response<>(userDto), HttpStatus.OK);
    }

    /**
     * Sign out.
     *
     * @param session Current http session
     * @return {@link Void}
     */
    @PostMapping("/auth/signOut")
    @LoginRequired
    public ResponseEntity<Response<Void>> signOut(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute(Constant.ME);

        if (user != null) {
            session.invalidate();
            sessionRepository.deleteById(session.getId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
