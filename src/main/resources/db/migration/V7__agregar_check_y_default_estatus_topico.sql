-- V7__agregar_check_y_default_estatus_topico.sql
-- Agrega DEFAULT y CHECK al campo estatus de la tabla topicos sin modificar su tipo ni nulabilidad

ALTER TABLE topicos
ALTER COLUMN estatus SET DEFAULT 'NO_RESPONDIDO';

ALTER TABLE topicos
ADD CONSTRAINT chk_estatus_topico
CHECK (
  estatus IN (
    'NO_RESPONDIDO',
    'NO_SOLUCIONADO',
    'SOLUCIONADO',
    'CERRADO'
  )
);
