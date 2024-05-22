package br.com.entregas.Entregas.core.validation;

import jakarta.validation.groups.Default;

public interface GroupValidation {
    interface Create extends Default{}
    interface Login extends Default{} 
}
