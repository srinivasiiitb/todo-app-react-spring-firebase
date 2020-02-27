/**
 * 
 */
package com.everestengineering.coding.todowebapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestengineering.coding.todowebapp.assembler.TodoItemModelAssembler;
import com.everestengineering.coding.todowebapp.model.TodoItem;
import com.everestengineering.coding.todowebapp.service.TodoItemService;

/**
 * @author Srinivas.Pakala
 *
 */

@RestController
public class TodoItemRestController {

	@Autowired
	private TodoItemService todoItemService;

	@Autowired
	private TodoItemModelAssembler assembler;

	@GetMapping("/allTodoItems")
	public CollectionModel<EntityModel<TodoItem>> getAllTodoItems(
			@RequestParam(required = false) Map<String, Object> queryParams) {

		List<EntityModel<TodoItem>> items = todoItemService.getAlltodoItems(queryParams).stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(items,
				linkTo(methodOn(TodoItemRestController.class).getAllTodoItems(queryParams)).withSelfRel());
	}

	@PostMapping("/allTodoItems")
	public ResponseEntity<?> todoItems(@RequestBody TodoItem newTodo) {

		EntityModel<TodoItem> TodoItemEntityModel = assembler.toModel(todoItemService.createNewTodoItem(newTodo));
		return ResponseEntity.created(TodoItemEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(TodoItemEntityModel);
	}

	@GetMapping("/allTodoItems/{id}")
	public EntityModel<TodoItem> getTodoItem(@PathVariable String id) throws Exception {
		return assembler.toModel(todoItemService.getTodoItem(id));
	}

	@PatchMapping(value = "/allTodoItems/{id}", consumes = "application/json", produces = "application/json")
	public EntityModel<TodoItem> updateTodoItem(@PathVariable String id, @RequestBody Map<String, Object> payload)
			throws Exception {
		return assembler.toModel(todoItemService.updateTodoItem(id, payload));
	}

	@DeleteMapping("/allTodoItems/{id}")
	public void deleteTodoItem(@PathVariable String id) throws Exception {
		todoItemService.deleteTodoItem(id);
	}

}
