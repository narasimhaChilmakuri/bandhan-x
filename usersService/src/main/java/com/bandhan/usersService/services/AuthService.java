package com.bandhan.usersService.services;


import com.bandhan.usersService.dto.LoginRequestDto;
import com.bandhan.usersService.dto.SignUpRequestDto;
import com.bandhan.usersService.dto.UserDto;
import com.bandhan.usersService.entity.Users;
import com.bandhan.usersService.events.UserCreatedEvent;
import com.bandhan.usersService.execption.BadRequestException;
import com.bandhan.usersService.repository.userRepository;
import com.bandhan.usersService.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final userRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    private final KafkaTemplate<Long,UserCreatedEvent> kafkaTemplate;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        log.info("Signing up user with email: {}", signUpRequestDto.getEmail());

        boolean exits = userRepository.existsByEmail(signUpRequestDto.getEmail());

        if(exits){
            throw new BadRequestException("User already exists with email: " + signUpRequestDto.getEmail());
        }

        Users user = modelMapper.map(signUpRequestDto, Users.class);
        user.setPassword(Bcrypt.hash(signUpRequestDto.getPassword()));
        user = userRepository.save(user);


        UserCreatedEvent  userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        kafkaTemplate.send("user_created_topic", userCreatedEvent);

        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email: {}", loginRequestDto.getEmail());

        Users user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password"));

        boolean isPasswordMatch = Bcrypt.verify(loginRequestDto.getPassword(), user.getPassword());

        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect email or password");
        }

        return jwtService.generateAccessToken(user);

    }
}
