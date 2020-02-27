/**
 * 
 */
package com.everestengineering.coding.todowebapp.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.everestengineering.coding.todowebapp.controller.TodoItemRestController;
import com.everestengineering.coding.todowebapp.model.TodoItem;

/**
 * @author Srinivas.Pakala
 *
 */

@Component
public class TodoItemModelAssembler implements RepresentationModelAssembler<TodoItem, EntityModel<TodoItem>> {

	
	  @Override
	  public EntityModel<TodoItem> toModel(TodoItem item) {

		  try {
			return new EntityModel<>(item,
					  linkTo(methodOn(TodoItemRestController.class).getTodoItem(item.getId())).withSelfRel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	  }
	  
	}