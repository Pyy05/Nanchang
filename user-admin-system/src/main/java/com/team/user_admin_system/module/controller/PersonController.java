package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Person;
import com.team.user_admin_system.module.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public List<Person> getPersons(
            @RequestParam(name = "dynasty", required = false) String dynasty,
            @RequestParam(name = "name", required = false) String name
    ) {
        if (name != null && !name.trim().isEmpty()) {
            return personService.searchByName(name);
        }
        if (dynasty == null || "全部时期".equals(dynasty) || "all".equals(dynasty)) {
            return personService.listAll();
        }
        return personService.listByDynasty(dynasty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        // 简化人物列表接口：GET /api/person/simple-list
    // 返回只包含核心字段(id, name, dynasty)的人物列表，用于下拉选择等轻量场景
    @GetMapping("/simple-list")
    public List<Map<String, Object>> getSimplePersonList() {
        // 获取所有人物数据
        List<Person> persons = personService.listAll();
        // 将 Person 对象转换为简化的 Map 结构，只保留关键信息
        return persons.stream().map(person -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", person.getPersonId());    // 人物ID
            map.put("name", person.getName());      // 人物姓名
            map.put("dynasty", person.getDynasty()); // 所属朝代
            return map;
        }).collect(Collectors.toList());
    }

}
