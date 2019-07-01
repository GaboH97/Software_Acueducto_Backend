CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_facturas_proc`()
BEGIN
-- declara variables de factura
DECLARE done INT DEFAULT 0;
DECLARE v_factura_id INT;
DECLARE v_factura_estado VARCHAR(2);
DECLARE v_factura_fecha_pago DATE;
DECLARE v_factura_fecha_maximo_pago DATE;
-- declara cursor para facturas
DECLARE factura_cursor CURSOR FOR SELECT id, fecha_pago, fecha_maximo_pago, estado_factura FROM facturas;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
OPEN factura_cursor;
my_loop: LOOP
	FETCH factura_cursor INTO v_factura_id, v_factura_fecha_pago, v_factura_fecha_maximo_pago, v_factura_estado;
		IF done = 1 THEN
            LEAVE my_loop;
		END IF;
		-- Si no se ha pagado y se ha pasado de la fecha de pago, pone la factura como vencida
		IF v_factura_fecha_pago IS NULL AND v_factura_fecha_maximo_pago < NOW() AND v_factura_estado = 'PP'  THEN
			UPDATE facturas SET estado_factura = 'VE' where id = v_factura_id;
		END IF;
END LOOP my_loop;
CLOSE factura_cursor;
END