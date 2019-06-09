insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('206-97-0192', 'Maryl', 'Hischke', '1', 'Y', '2019-03-02', 'F', '523-796-5041', 'mhischke0@opera.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('700-32-6247', 'Davin', 'Alliston', '0', 'Z', '2019-05-05', 'M', '359-455-1871', 'dalliston1@whitehouse.gov');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('550-47-0813', 'Josefina', 'Sutton', 'R', 'X', '2019-04-16', 'F', '922-928-9954', 'jsutton2@sfgate.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('422-69-2735', 'Bessy', 'Rivard', 'R', 'Q', '2018-10-30', 'F', '742-579-5414', 'brivard3@ezinearticles.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('445-73-6498', 'Kendell', 'Nottingham', 'L', 'L', '2019-04-12', 'M', '480-210-1477', 'knottingham4@boston.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('745-41-5802', 'Rayner', 'Lambe', 'O', 'S', '2018-12-02', 'M', '976-252-4384', 'rlambe5@nifty.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('633-65-7368', 'Cortney', 'Jaulmes', 'I', '5', '2018-11-24', 'F', '558-848-1343', 'cjaulmes6@eepurl.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('458-67-6207', 'Jereme', 'Donoher', 'S', 'F', '2019-01-25', 'M', '517-342-4205', 'jdonoher7@printfriendly.com');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('552-47-9232', 'Vida', 'Fisher', 'C', 'F', '2019-03-26', 'F', '617-216-3643', 'vfisher8@state.gov');
insert into suscriptores (cedula, nombre, apellido, estado, estado_cuenta, fecha_nacimiento, genero, numero_telefono, correo_electronico) values ('529-26-7764', 'Ripley', 'Glaister', 'U', '1', '2018-09-29', 'M', '862-323-8542', 'rglaister9@booking.com');


insert into lugares (id, nombre, tipo, ubicado_id) values (1,'Colombia','P', null)

insert into lugares (id, nombre, tipo, ubicado_id) values (2,'Boyaca','D', 1)

insert into lugares (id, nombre, tipo, ubicado_id) values (3,'Meta','D', 1)

insert into predios (numero_matricula, direccion, estrato, latitud, longitud, lugar_id) values ('2','hjsdkfhds',1, 1.1,1.2,2)

insert into predios (numero_matricula, direccion, estrato, latitud, longitud, lugar_id) values ('3','hjsdkfhds',1, 1.1,1.2,2)

insert into asignaciones (fecha_final, fecha_inicial,suscriptor_cedula, predio_lugar_id, predio_numero_matricula) values ('2018-09-29', '2018-09-29','206-97-0192',2,'2')

insert into asignaciones (fecha_final, fecha_inicial,suscriptor_cedula, predio_lugar_id, predio_numero_matricula) values ('2018-09-29', '2018-09-29','206-97-0192',2,'3')
