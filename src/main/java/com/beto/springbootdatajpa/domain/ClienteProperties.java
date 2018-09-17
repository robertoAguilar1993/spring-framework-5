package com.beto.springbootdatajpa.domain;

/**
 * @author beto
 */
public enum ClienteProperties {
        CLIENTE_ROOTPATH("cliente.rootpatch"),
         CLIENTE_ERROR("cliente.error"),
         CLIENTE_SUCCESS("cliente.success"),
         CLIENTE_INFO("cliente.info"),
         CLIENTE_REDIRECT_CLIENTES("cliente.redirect.clientes"),
         CLIENTE_CLIENTE("cliente.cliente"),
         CLIENTE_TITULO("cliente.titulo"),
         CLIENTE_FORMULARIO_CLIENTE("cliente.formulario.cliente"),
         CLIENTE_REDIRECT_CLINETE_FORM("cliente.redirect.cliente.form");

        private String  value;

        ClienteProperties(String value) {
                this.value = value;
        }

        public String getValue() {
                return value;
        }
}
