
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_suscriptor_proc`(IN factura_id INT)
BEGIN
-- declara variables de factura
DECLARE v_suscriptor_cedula VARCHAR(10);
DECLARE v_suscriptor_estado_cuenta VARCHAR (2);
DECLARE v_numero_facturas_vencidas INT;
-- declara cursor para facturas

SELECT S.CEDULA, S.ESTADO_CUENTA INTO v_suscriptor_cedula, v_suscriptor_estado_cuenta
FROM SUSCRIPTORES S JOIN PREDIOS P ON S.CEDULA = P.SUSCRIPTOR_CEDULA
JOIN FACTURAS F ON F.PREDIO_NUMERO_MATRICULA = P.NUMERO_MATRICULA 
WHERE F.ID = factura_id;

SELECT COUNT(F.ID) AS FACTURAS_VENCIDAS INTO v_numero_facturas_vencidas
FROM SUSCRIPTORES S JOIN PREDIOS P ON S.CEDULA = P.SUSCRIPTOR_CEDULA
JOIN FACTURAS F ON F.PREDIO_NUMERO_MATRICULA = P.NUMERO_MATRICULA 
WHERE F.ESTADO_FACTURA ='VE' AND S.CEDULA= v.suscriptor_cedula;

IF(v_numero_facturas_vencidas > 0) THEN
	UPDATE SUSCRIPTORES SET ESTADO_CUENTA = 'M' WHERE CEDULA = v_suscriptor_cedula;
END IF;
END$$
DELIMITER ;