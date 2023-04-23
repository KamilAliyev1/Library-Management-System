package com.FinalProject.service;

import java.util.List;

public interface OrderService<GET,POST,PUT> {

    GET add(POST dto);

    GET get(Long ID);

    GET update(Long id,PUT dto);

    void delete(Long ID);

    void disableProgress(Long ID);

    List<GET> getAll();

}
