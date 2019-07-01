CREATE DEFINER=`root`@`localhost` TRIGGER `facturas_actualizar_fecha_pago_trig` BEFORE UPDATE ON `facturas` FOR EACH ROW BEGIN
-- Si la fecha de pago ha cambiado, actualiza la factura con estado PA (Pagada)
	IF OLD.fecha_pago IS NULL AND NEW.fecha_pago IS NOT NULL THEN
		SET NEW.estado_factura='PA';
    END IF;
END