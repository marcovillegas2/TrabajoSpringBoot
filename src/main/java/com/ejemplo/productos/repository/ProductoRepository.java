package com.ejemplo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejemplo.productos.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
