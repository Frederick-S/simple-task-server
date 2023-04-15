package dekiru.simpletask.support;

import dekiru.simpletask.Constant;
import dekiru.simpletask.annotation.Me;
import dekiru.simpletask.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * If an argument of a route is annotated by {@link dekiru.simpletask.annotation.Me}
 * and its type is {@link dekiru.simpletask.dto.UserDto},
 * then the value of this argument is resolved to current login user in this session.
 */
@Component
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Me.class) != null
                && UserDto.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpSession session = ((HttpServletRequest) webRequest.getNativeRequest()).getSession();

        return session.getAttribute(Constant.ME);
    }
}
