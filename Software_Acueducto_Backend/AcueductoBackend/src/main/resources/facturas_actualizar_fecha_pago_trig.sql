DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `facturas_actualizar_fecha_pago_trig` AFTER UPDATE ON `facturas` FOR EACH ROW
BEGIN
-- Si la fecha de pago ha cambiado, actualiza la factura con estado PA (Pagada)
	CALL actualizar_suscriptor_proc(NEW.id);
END$$
DELIMITER ;