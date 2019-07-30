package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.customEntities.ReporteVereda;
import com.acueducto.backend.models.entity.Lugar;

public interface ILugarDAO extends CrudRepository<Lugar, Integer>{

	public List<Lugar> findByTipo(String tipo);
	
	public Lugar findByNombreIgnoreCase(String nombre);
	
	@Query("select count(p) from Predio p where p.vereda.id=?1 ")
	public int numeroPrediosAsociados(int id);
	
	@Query(nativeQuery = true, value = "SELECT VEREDA AS nombreVereda, SUM(Z.TOTAL) AS totalRecaudo FROM (SELECT F.ID, SUM(D.valor) AS TOTAL, L.NOMBRE AS VEREDA FROM FACTURAS F JOIN DETALLES_FACTURA D ON F.ID = D.FACTURA_ID JOIN PREDIOS P ON P.NUMERO_MATRICULA = F.PREDIO_NUMERO_MATRICULA JOIN LUGARES L ON P.LUGAR_ID = L.ID GROUP BY F.ID) Z GROUP BY VEREDA")
	public List<ReporteVereda> obtenerReporteRecaudosVereda();
}
