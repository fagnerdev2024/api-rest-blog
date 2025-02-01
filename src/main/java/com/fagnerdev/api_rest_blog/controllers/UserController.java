package com.fagnerdev.api_rest_blog.controllers;

import com.fagnerdev.api_rest_blog.domain.Post;
import com.fagnerdev.api_rest_blog.domain.User;
import com.fagnerdev.api_rest_blog.dto.UserDTO;
import com.fagnerdev.api_rest_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> buscarTodos() {
        List<User> list = userService.buscarTodosOsUsuarios();
        List<UserDTO> listDto = list.stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User obj = userService.buscarUsuarioPorId(id);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
        User obj = userService.pegarDoDto(objDto);
        obj = userService.inserirUsuario(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
        User obj = userService.pegarDoDto(objDto);
        obj.setId(id);
        obj = userService.atualizarUsuario(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
        User obj = userService.buscarUsuarioPorId(id);
        return ResponseEntity.ok().body(obj.getPosts());
    }
}