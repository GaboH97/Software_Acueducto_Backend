
/*INSERT SUSCRIPTORES*/

insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('2069701923', 'Maryl', 'Hischke', 'A', 'D', '2019-03-02', 'F', '523-796-5041', 'mhischke0@opera.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('7003262474', 'Davin', 'Alliston', 'A', 'D', '2019-05-05', 'M', '359-455-1871', 'dalliston1@whitehouse.gov');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('5504708135', 'Josefina', 'Sutton', 'A', 'D', '2019-04-16', 'F', '922-928-9954', 'jsutton2@sfgate.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('4226927356', 'Bessy', 'Rivard', 'I', 'D', '2018-10-30', 'F', '742-579-5414', 'brivard3@ezinearticles.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('4457364987', 'Kendell', 'Nottingham', 'A', 'M', '2019-04-12', 'M', '480-210-1477', 'knottingham4@boston.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('7454158028', 'Rayner', 'Lambe', 'A', 'M', '2018-12-02', 'M', '976-252-4384', 'rlambe5@nifty.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('6336573689', 'Cortney', 'Jaulmes', 'A', 'D', '2018-11-24', 'F', '558-848-1343', 'cjaulmes6@eepurl.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('4586762070', 'Jereme', 'Donoher', 'A', 'D', '2019-01-25', 'M', '517-342-4205', 'jdonoher7@printfriendly.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('5524792321', 'Vida', 'Fisher', 'I', 'D', '2019-03-26', 'F', '617-216-3643', 'vfisher8@state.gov');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('5292677642', 'Ripley', 'Glaister', 'I', 'M', '2018-09-29', 'M', '862-323-8542', 'rglaister9@booking.com');

--Prueba de no duplicado 

--insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('529-26-7764', 'Ripley', 'Glaister', 'U', '1', '2018-09-29', 'M', '862-323-8542', 'rglaister9@booking.com');


/*INSERT LUGARES*/

insert into lugares (nombre, tipo, ubicado_id) values ('Moniquira','M', null)
insert into lugares (nombre, tipo, ubicado_id) values ('Paipa','M', null)
insert into lugares (nombre, tipo, ubicado_id) values ('Santa Ana','M', null)
insert into lugares (nombre, tipo, ubicado_id) values ('Pila Grande','V', 1)
insert into lugares (nombre, tipo, ubicado_id) values ('Potrero Grande','V', 1)
insert into lugares (nombre, tipo, ubicado_id) values ('La Hoya','V', 1)

--Prueba de identidad referencial. Mismo ID 
--insert into lugares (id, nombre, tipo, ubicado_id) values (3,'Meta','D', 2)

--Prueba de duplicados. Mismo Ubicado no existe 
--insert into lugares (id, nombre, tipo, ubicado_id) values (3,'Meta','D', 1)

/*INSERT EMPLEADOS*/

insert into empleados (cedula, nombre, apellido, fecha_nacimiento, tipo_empleado, usuario, contrasena, genero, direccion_residencia, lugar_id, activo) values ('2069701934', 'Gabriel', 'Huertas','2019-03-02', 'A', 'mhischke','$2a$10$olDqLNiWWyq/f9RlMu.71.5MptG5h9TKnMTsCUKMrChPTUFRHFOYu','F','Calle falsa 123',2, 1);
insert into empleados (cedula, nombre, apellido, fecha_nacimiento, tipo_empleado, usuario, contrasena, genero, direccion_residencia, lugar_id, activo) values ('2069701923', 'Admin', 'Pro','2019-03-02', 'A', 'mhischke1','$2a$10$YFdPmP7PyoM8Naxs0NPClupAG4rhspHbRo6y.E6OnqkNyQgpvNGtm','F','Calle falsa 123',2, 1);

/*INSERT PREDIOS*/


insert into predios (numero_matricula, nombre, estrato, latitud, longitud, lugar_id, suscriptor_cedula) values ('080-7353','El rodeo',1 , 1.1, 1.2, 4,'2069701923');

insert into predios (numero_matricula, nombre, estrato, latitud, longitud, lugar_id, suscriptor_cedula) values ('080-7354','Uberrimo',1 , 1.1, 1.2, 4, '2069701923');

insert into predios (numero_matricula, nombre, estrato, latitud, longitud, lugar_id, suscriptor_cedula) values ('080-7355','Ubate',1 , 1.1, 1.2, 4, '7003262474');

/*INSERT ASIGNACIONES

--insert into asignaciones (fecha_inicial, fecha_final, cedula_suscriptor, lugar_id, predio_matricula) values ('2018-09-29', '2018-09-29','206-97-0192',2,'2')

--insert into asignaciones (fecha_inicial, fecha_final, cedula_suscriptor, lugar_id, predio_matricula) values ('2018-09-29', '2018-09-29','700-32-6247',2,'2')



/*INSERT FACTURAS*/

insert into FACTURAS (fecha_emision, fecha_maximo_pago, fecha_pago, estado_factura, predio_numero_matricula, periodo_facturado) values ('2018-12-20','2018-01-04','2018-01-03','PA','080-7353', '2018-12-01');
insert into FACTURAS (fecha_emision, fecha_maximo_pago, fecha_pago, estado_factura, predio_numero_matricula, periodo_facturado) values ('2018-12-20','2018-01-04','2018-01-03','PA','080-7353', '2018-12-01');
insert into FACTURAS (fecha_emision, fecha_maximo_pago, fecha_pago, estado_factura, predio_numero_matricula, periodo_facturado) values ('2018-12-20','2018-01-04','2018-01-03','PA','080-7354', '2018-12-01');
insert into FACTURAS (fecha_emision, fecha_maximo_pago, estado_factura, predio_numero_matricula, periodo_facturado) values ('2019-06-1','2018-07-01','PP','080-7355', '2019-07-01');



/*INSERT TARIFAS*/

insert into TARIFAS (descripcion, valor_tarifa) values ('Valor metro cúbico', 500);
insert into TARIFAS (descripcion, valor_tarifa) values ('Jornal',  27604);
insert into TARIFAS (descripcion, valor_tarifa) values ('Concesión Corpoboyacá', 5000);
insert into TARIFAS (descripcion, valor_tarifa) values ('Compra de predio planta de tratamiento', 50000);
insert into TARIFAS (descripcion, valor_tarifa) values ('Cobro por reconexión', 25000);

/*INSERT DETALLES_FACTURA*/

insert into DETALLES_FACTURA (factura_id, tarifa_id, cantidad, valor) values (1,1,1,500);
insert into DETALLES_FACTURA (factura_id, tarifa_id, cantidad, valor) values (1,2,1,27.604);
insert into DETALLES_FACTURA (factura_id, tarifa_id, cantidad, valor) values (2,1,1,500);
insert into DETALLES_FACTURA (factura_id, tarifa_id, cantidad, valor) values (3,1,2,500);
insert into DETALLES_FACTURA (factura_id, tarifa_id, cantidad, valor) values (4,1,2,500);

insert into ROLES (nombre) values ('ROLE_ADMIN');
insert into ROLES (nombre) values ('ROLE_USER');

insert into EMPLEADOS_ROLES (empleado_cedula, roles_id) values ('2069701923',1);
insert into EMPLEADOS_ROLES (empleado_cedula, roles_id) values ('2069701934',2);
insert into EMPLEADOS_ROLES (empleado_cedula, roles_id) values ('2069701923',2);  