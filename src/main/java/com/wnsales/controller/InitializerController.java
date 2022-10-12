package com.wnsales.controller;

import com.wnsales.util.Initializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * End point para inicializar dados para testes
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/initializer")
@Tag(name = "Data Initializer")
public class InitializerController {

	@Autowired
	private Initializer initializer;

	@PostMapping("/init")
	@Operation(summary = "Create data for test")
	public String initializer() {
		initializer.init();
		return "OK";
	}

}


