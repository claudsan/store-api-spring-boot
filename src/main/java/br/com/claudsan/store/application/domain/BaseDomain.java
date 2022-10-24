package br.com.claudsan.store.application.domain;

import java.io.Serializable;

public interface BaseDomain extends Serializable {
    Serializable toDTO();
}
