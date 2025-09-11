ALTER TABLE patient
ADD CONSTRAINT uq_patient_uuid UNIQUE (uuid);