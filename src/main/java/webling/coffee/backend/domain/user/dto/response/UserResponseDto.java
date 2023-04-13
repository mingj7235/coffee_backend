package webling.coffee.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.enums.UserRole;

import java.time.LocalDate;

public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Register {
        private String email;
        private String username;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String userRole;
        private String teamName;

        public static Register toDto(final @NotNull User entity) {
            return Register.builder()
                    .email(entity.getEmail())
                    .username(entity.getUsername())
                    .phoneNumber(entity.getPhoneNumber())
                    .birthDate(entity.getBirthDate())
                    .userRole(entity.getUserRole().name())
                    .teamName(entity.getTeamName().name())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Login {
        private Long userId;
        private String email;
        private String username;

        public static Login toDto(final @NotNull User user) {
            return Login.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
        }
    }
}
