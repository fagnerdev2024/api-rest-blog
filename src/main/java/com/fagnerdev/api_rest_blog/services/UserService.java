package com.fagnerdev.api_rest_blog.services;

import com.fagnerdev.api_rest_blog.domain.User;
import com.fagnerdev.api_rest_blog.dto.UserDTO;
import com.fagnerdev.api_rest_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> buscarTodosOsUsuarios() {
        return userRepository.findAll();
    }

    public User buscarUsuarioPorId(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));
    }

    public User inserirUsuario(User user) {
        return userRepository.insert(user);
    }

    public void deletarUsuario(String id) {
        buscarUsuarioPorId(id);
        userRepository.deleteById(id);
    }

    public User atualizarUsuario(User user) {
        User novoUsuario = buscarUsuarioPorId(user.getId());
        atualizarDados(novoUsuario, user);
        return userRepository.save(novoUsuario);
    }

    private void atualizarDados(User newUser, User user) {
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
    }

    public User pegarDoDto(UserDTO userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}
