package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Person;
import com.team.user_admin_system.module.entity.Event;
import com.team.user_admin_system.module.entity.EventPerson;
import com.team.user_admin_system.module.service.PersonService;
import com.team.user_admin_system.module.repository.EventPersonRepository;
import com.team.user_admin_system.module.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EventPersonRepository eventPersonRepository;

    @Autowired
    private EventRepository eventRepository;

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

    /**
     * 根据人物名称查找人物ID
     * 地址：GET /api/person/findByName?name=xxx
     */
    @GetMapping("/findByName")
    public ResponseEntity<Map<String, Object>> findByName(@RequestParam String name) {
        List<Person> persons = personService.searchByName(name);
        if (!persons.isEmpty()) {
            Person p = persons.get(0);
            Map<String, Object> result = new HashMap<>();
            result.put("personId", p.getPersonId());
            result.put("name", p.getName());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 获取人物的相关事件
     * 地址：GET /api/person/{id}/related-events
     */
    @GetMapping("/{id}/related-events")
    public List<Map<String, Object>> getRelatedEvents(@PathVariable Long id) {
        List<EventPerson> relations = eventPersonRepository.findByPersonId(id.intValue());
        return relations.stream().map(ep -> {
            Optional<Event> eventOpt = eventRepository.findById(ep.getEventId().longValue());
            Map<String, Object> map = new HashMap<>();
            if (eventOpt.isPresent()) {
                Event e = eventOpt.get();
                map.put("eventId", e.getEventId());
                map.put("eventName", e.getEventName());
                map.put("dynasty", e.getDynasty());
                map.put("occurTime", e.getOccurTime());
            }
            return map;
        }).filter(m -> !m.isEmpty()).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> savePerson(@RequestBody Person person) {
        Person saved = personService.savePerson(person);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "success");
        result.put("data", saved);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }
}

