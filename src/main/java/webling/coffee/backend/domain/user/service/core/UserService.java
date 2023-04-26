package webling.coffee.backend.domain.user.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.repository.UserRepository;
import webling.coffee.backend.global.responses.errors.codes.CouponErrorCode;
import webling.coffee.backend.global.responses.errors.codes.UserErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkDuplicationUser (UserRequestDto.Register request) {
        return userRepository.checkUserByEmail(request.getEmail());
    }

    public User register(UserRequestDto.Register request) {
        return userRepository.save(User.register(request));
    }

    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RestBusinessException.NotFound(UserErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findById(final @NotNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RestBusinessException.NotFound(UserErrorCode.NOT_FOUND));
    }

    public User update(final @NotNull User user, final @NotNull UserRequestDto.Update request) {
        return userRepository.save(User.update(user, request));
    }

    public User updateRole(final @NotNull User user, final @NotNull UserRequestDto.UpdateRole request) {
        return userRepository.save(User.updateRole(user, request));
    }
}
