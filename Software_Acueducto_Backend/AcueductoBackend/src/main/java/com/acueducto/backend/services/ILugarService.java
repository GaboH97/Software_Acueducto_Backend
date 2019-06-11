package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Lugar;

public interface ILugarService {

	public List<Lugar> findAll();

	public void save(Lugar lugar);

	public Lugar findById(int id);

	public void delete(int id);
}
