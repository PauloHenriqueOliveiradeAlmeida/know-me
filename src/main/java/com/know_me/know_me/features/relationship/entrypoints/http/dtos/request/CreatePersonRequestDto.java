package com.know_me.know_me.features.relationship.entrypoints.http.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreatePersonRequestDto (
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
	String name,

	Set<
		@NotBlank(message = "Interesses é obrigatório")
		@Size(min = 3, max = 100, message = "O interesse deve ter entre 3 e 100 caracteres")
		String> interests
) {}