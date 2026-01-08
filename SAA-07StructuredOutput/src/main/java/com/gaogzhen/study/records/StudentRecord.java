package com.gaogzhen.study.records;

import java.io.Serializable;

/**
 *
 * @author gaogzhen
 * @create 2026-01-08
 */
public record StudentRecord(Long id, String name, String major, String email) implements Serializable { }
