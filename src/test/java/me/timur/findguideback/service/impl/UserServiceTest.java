//package me.timur.findguideback.service.impl;
//
//import me.timur.findguideback.entity.User;
//import me.timur.findguideback.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Test
//    void getOrSave() {
//        var userOpt = Optional.of(new User());
//        when(userRepository.findByTelegramId(anyLong())).thenReturn(userOpt);
//        when(userRepository.save(any(User.class))).thenReturn(new User());
//    }
//
//    @Test
//    void getById() {
//    }
//
//    @Test
//    void getByTelegramId() {
//    }
//}