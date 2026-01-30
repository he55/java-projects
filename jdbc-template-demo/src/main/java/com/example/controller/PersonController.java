package com.example.controller;

import com.example.mapper.PersonMapper;
import com.example.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Person> getList() {
        List<Person> list = jdbcTemplate.query("select * from person", new PersonMapper());
        return list;
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable int id) {
        return jdbcTemplate.queryForObject("select * from person where id = ?", new PersonMapper(), id);
    }

    @GetMapping("/count")
    public Integer getCount() {
        return jdbcTemplate.queryForObject("select count(*) from person", Integer.class);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
