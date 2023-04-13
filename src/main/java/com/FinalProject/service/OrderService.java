package com.FinalProject.service;

public interface OrderService<GET,POST,PUT> {

    GET add(POST dto);

    GET get(Long ID);

    GET update(PUT dto);

    void delete(Long ID);

    void disableProgress(Long ID);

}
